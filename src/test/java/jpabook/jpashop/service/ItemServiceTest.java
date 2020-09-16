package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void 상품_등록(){
        // given
        Item movie = new Movie();
        Item album = new Album();
        Item book = new Book();
        movie.setName("영화");
        album.setName("앨범");
        book.setName("책");

        // when
        itemService.saveItem(movie);
        itemService.saveItem(album);
        itemService.saveItem(book);

        //then
        assertEquals(movie,itemRepository.findOne(movie.getId()));
        assertEquals(album,itemRepository.findOne(album.getId()));
        assertEquals(book,itemRepository.findOne(book.getId()));
    }
    
}