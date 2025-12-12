package com.surest.members.service;

import com.surest.members.dto.MemberDto;
import com.surest.members.entity.Member;
import com.surest.members.repository.MemberRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* ========================
       Controller-facing methods (entity types)
       These match signatures your MemberController expects:
       - list(...)
       - getById(...)
       - create(...)
       - update(...)
       - delete(...)
       ======================== */

    /**
     * Controller expects to pass page/size and optional filters/sort.
     * This returns a Page<Member> (entity) to match your controller.
     */
    @Transactional(readOnly = true)
    public Page<Member> list(int page, int size, String sort, String firstName, String lastName) {
        Sort sortObj = Sort.unsorted();
        if (sort != null && !sort.isBlank()) {
            // expected format: "property,asc" or "property,desc"
            String[] parts = sort.split(",");
            if (parts.length >= 1) {
                Sort.Direction dir = (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
                sortObj = Sort.by(dir, parts[0]);
            }
        }

        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), sortObj);

        // NOTE: This example does NOT implement filtering by firstName/lastName.
        // If filters provided, you could use Specifications or a custom query.
        Page<Member> pageRes = memberRepository.findAll(pageable);
        return pageRes;
    }

    @Transactional(readOnly = true)
    public Optional<Member> getById(UUID id) {
        return memberRepository.findById(id);
    }

    public Member create(Member member) {
        // ensure unique email
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> { throw new IllegalArgumentException("Email already exists: " + member.getEmail()); });

        // id & timestamps handled by entity @PrePersist
        return memberRepository.save(member);
    }

    public Member update(UUID id, Member memberPayload) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        existing.setFirstName(memberPayload.getFirstName());
        existing.setLastName(memberPayload.getLastName());
        existing.setDateOfBirth(memberPayload.getDateOfBirth());
        existing.setEmail(memberPayload.getEmail());
        // updatedAt managed by @PreUpdate in entity

        return memberRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("Member not found: " + id);
        }
        memberRepository.deleteById(id);
    }

    /* ========================
       DTO-based methods (optional helpers)
       If other code/tests use DTOs, these methods convert between entity & DTO
       ======================== */

    @Transactional(readOnly = true)
    public Page<MemberDto> listMembers(Optional<String> firstNameFilter,
                                       Optional<String> lastNameFilter,
                                       Pageable pageable) {

        Page<Member> page = memberRepository.findAll(pageable);
        return page.map(entityToDtoMapper());
    }

    @Transactional(readOnly = true)
    public Optional<MemberDto> getMemberById(UUID id) {
        return memberRepository.findById(id).map(entityToDtoMapper());
    }

    public MemberDto createMember(MemberDto dto) {
        memberRepository.findByEmail(dto.getEmail()).ifPresent(m -> {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        });

        Member entity = dtoToEntityMapper().apply(dto);
        Member saved = memberRepository.save(entity);
        return entityToDtoMapper().apply(saved);
    }

    public MemberDto updateMember(UUID id, MemberDto dto) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setEmail(dto.getEmail());

        Member saved = memberRepository.save(existing);
        return entityToDtoMapper().apply(saved);
    }

    public void deleteMember(UUID id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("Member not found: " + id);
        }
        memberRepository.deleteById(id);
    }

    /* ---------------------- Helpers: mappers ---------------------- */

    private Function<Member, MemberDto> entityToDtoMapper() {
        return entity -> {
            MemberDto dto = new MemberDto();
            dto.setId(entity.getId());
            dto.setFirstName(entity.getFirstName());
            dto.setLastName(entity.getLastName());
            dto.setDateOfBirth(entity.getDateOfBirth());
            dto.setEmail(entity.getEmail());

            // convert Instant (entity) -> OffsetDateTime (dto)
            if (entity.getCreatedAt() != null) {
                dto.setCreatedAt(OffsetDateTime.ofInstant(entity.getCreatedAt(), ZoneOffset.UTC));
            }
            if (entity.getUpdatedAt() != null) {
                dto.setUpdatedAt(OffsetDateTime.ofInstant(entity.getUpdatedAt(), ZoneOffset.UTC));
            }

            return dto;
        };
    }

    private Function<MemberDto, Member> dtoToEntityMapper() {
        return dto -> {
            Member entity = new Member();
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setDateOfBirth(dto.getDateOfBirth());
            entity.setEmail(dto.getEmail());
            return entity;
        };
    }
}
