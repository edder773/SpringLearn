package study.demo.member;

public interface MemberRepository {
    void save(Member member);
    Member findByid(Long memberId);
}
