package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    @Transactional
    public void updateItem(Long itemId, UpdateItemDto itemDto){
        // 영속성 엔티티를 find메소드로 찾아옴(jpa가 변경을 감지하고 있음)
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(itemDto.getName());
        findItem.setPrice(itemDto.getPrice());
        findItem.setStockQuantity(itemDto.getStockQuantity());
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }
}
