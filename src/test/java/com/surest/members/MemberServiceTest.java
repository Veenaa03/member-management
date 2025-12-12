package com.surest.members;

import com.surest.members.entity.Member;
import com.surest.members.repository.MemberRepository;
import com.surest.members.service.MemberService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    @Mock MemberRepository repo;
    @InjectMocks MemberService service;

    @BeforeEach
    void init(){ MockitoAnnotations.openMocks(this); }

    @Test
    void list_returnsPage() {
        Page<Member> page = new PageImpl<>(List.of(new Member()));
        when(repo.findAll(any(Pageable.class))).thenReturn(page);

        Page<Member> res = service.list(0, 10, "firstName,asc", "", "");

        assertEquals(1, res.getTotalElements());
    }
}
