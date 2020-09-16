package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 등록
    public void save(Item item){
        if(item.getId() == null){ // 아이템을 새로 등록할때는 id값이 없기때문에
            em.persist(item);
        }else{ // 이미 아이템이 db에 등록되어있을때
            em.merge(item);
        }
    }

    // 한 건 조회
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    // 전체조회
    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
