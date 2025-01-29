package br.com.jademe.transactions.application.controller;

import br.com.jademe.transactions.business.dto.TimelineDTO;
import br.com.jademe.transactions.business.service.TimelineService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/timeline", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimelineController {

    @NonNull
    private final TimelineService timelineService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody final TimelineDTO timelineDTO) {
        timelineService.saveToDynamo(timelineDTO);
    }
}
