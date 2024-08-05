package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RequiredArgsConsturctor : 아래 1,2 와 같음(itemRepository Bean 등록, 생성자 주입
 *
 * private final ItemRepository itemRepository;
 *
 * ```java
 *
 * 1.
 * private final ItemRepository itemRepository;
 *
 * @Autowired
 * public BasicItemRepository(ItemRespository itemRepository) {
 *      this.itemRepository = itemRepository;
 * }
 *
 * 2.
 * private final ItemRepository itemRepository;
 *
 * public BasicItemRepository(ItemRepository itemRepository) {
 *     this.itemRepository = itemRepository;
 * }
 *
 * BasicItemController(Item)
 * ```
 * */

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "redirect:/basic/items/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute("item") Item item) {
        itemRepository.update(itemId, item);
        return "basic/item";
    }


    /**
     * 테스트용 데이터
     * @PostContruct : Bean 이 초기화되고 의존성 주입이 이루어진 후 딱 한 번 초기화를 수행함, Service 로직을 타기전에 수행함
     * */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
