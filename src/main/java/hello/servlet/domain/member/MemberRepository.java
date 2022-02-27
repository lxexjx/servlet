package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 동시성 문제가 고려돼있지 않아 실무에서는 ConcurrentHashMap,AtomicLong사용 고려
*/
public class MemberRepository {

    //static->memeberRepository가 new로 많이 생성해줘도 하나만 사용
    private static Map<Long,Member> store=new HashMap<>();
    private static long sequence=0L;

    private static final MemberRepository instance =new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }
    private MemberRepository(){
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return  member;
    }

    public Member findById(Long id){
        return store.get(id);
    }
    public List<Member> findAll(){
        return new ArrayList<>(store.values());     //store에 있는 모든 값 꺼내서 리스트에 담아서 넘겨줘->store에 있는 값을 건드리지 않으려고
    }

    public void clearStore(){
        store.clear();
    }
}
