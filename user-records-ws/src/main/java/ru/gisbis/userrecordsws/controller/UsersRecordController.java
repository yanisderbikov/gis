package ru.gisbis.userrecordsws.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gisbis.userrecordsws.entity.RecordEntity;
import ru.gisbis.userrecordsws.repository.RecordRepository;
import ru.gisbis.userrecordsws.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class UsersRecordController {

    private final RecordRepository repository;

    private final UserRepository userRepository;

    @GetMapping("/records")
    public String indexPage(@RequestParam("username") String userName, final Model model) {

        var map = Flux.from(userRepository.findByName(userName)
                .map(user -> repository.findAllByUserId(user.getId())))
                .flatMap(flux -> flux);

        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(map, 1);
        model.addAttribute("records", reactiveDataDrivenMode);
        return "index";
    }


}

