package com.daejeongwang.uoscrazydaejeon.service;

import com.daejeongwang.uoscrazydaejeon.entity.Member;
import com.daejeongwang.uoscrazydaejeon.repository.*;
import com.daejeongwang.uoscrazydaejeon.util.AppleUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberDeletionTest {
    @Mock MemberRepository members;
    @Mock RefreshTokenRepository refresh;
    @Mock RewardDrawLogRepository rewards;
    @Mock ReceiptRepository receipts;
    @Mock VisitedPlaceRepository visits;
    @Mock PlacePhotoRepository photos;
    @Mock PlaceClickLogRepository clicks;
    @Mock AppleRefreshTokenRepository appleTokens;
    @Mock AppleUtil apple;
    @Mock S3Service s3;
    @InjectMocks MemberService service;

    @Test
    void deletesPhotoAndReceiptFilesBeforeDatabaseRecords() {
        var member = Member.builder().id(1L).build();
        when(members.findById(1L)).thenReturn(Optional.of(member));
        when(photos.findObjectKeysByMemberId(1L)).thenReturn(List.of("photo-a", "photo-b"));
        when(receipts.findObjectKeysByMemberId(1L)).thenReturn(List.of("receipt-a"));
        service.deleteMember(1L);
        var order = inOrder(s3, receipts, photos, members);
        order.verify(s3).deleteObject("photo-a");
        order.verify(s3).deleteObject("photo-b");
        order.verify(s3).deleteObject("receipt-a");
        order.verify(receipts).deleteAllByVisitedPlace_Member_Id(1L);
        order.verify(photos).deleteAllByMember_Id(1L);
        order.verify(members).delete(member);
        verifyNoMoreInteractions(s3);
    }

    @Test
    void keepsDatabaseKeysWhenReceiptFileDeletionFails() {
        when(members.findById(1L)).thenReturn(Optional.of(Member.builder().id(1L).build()));
        when(photos.findObjectKeysByMemberId(1L)).thenReturn(List.of("photo"));
        when(receipts.findObjectKeysByMemberId(1L)).thenReturn(List.of("receipt"));
        doAnswer(invocation -> {
            if ("receipt".equals(invocation.getArgument(0))) {
                throw new IllegalStateException("S3 unavailable");
            }
            return null;
        }).when(s3).deleteObject(anyString());
        assertThrows(IllegalStateException.class, () -> service.deleteMember(1L));
        verify(s3).deleteObject("photo");
        verify(receipts, never()).deleteAllByVisitedPlace_Member_Id(anyLong());
        verify(photos, never()).deleteAllByMember_Id(anyLong());
        verify(members, never()).delete(any(Member.class));
    }

    @Test
    void memberWithoutFilesCanWithdraw() {
        var member = Member.builder().id(1L).build();
        when(members.findById(1L)).thenReturn(Optional.of(member));
        service.deleteMember(1L);
        verifyNoInteractions(s3);
        verify(members).delete(member);
    }
}
