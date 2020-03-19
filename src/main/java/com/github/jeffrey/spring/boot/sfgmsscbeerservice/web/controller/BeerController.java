package com.github.jeffrey.spring.boot.sfgmsscbeerservice.web.controller;

import com.github.jeffrey.spring.boot.sfgmsscbeerservice.web.model.BeerDto;
import com.github.jeffrey.spring.boot.sfgmsscbeerservice.web.model.BeerPagedList;
import com.github.jeffrey.spring.boot.sfgmsscbeerservice.web.model.BeerStyleEnum;
import com.github.jeffrey.spring.boot.sfgmsscbeerservice.web.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by jeffreymzd on 3/15/20
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
@Slf4j
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private final BeerService beerService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(beerList, HttpStatus.OK);

    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@NotNull @PathVariable("beerId") UUID beerId) {

        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto) {

        val savedDto = beerService.save(beerDto);

        // todo impl so savedDto should return valid UUID
        if (null == savedDto.getId()) savedDto.setId(UUID.randomUUID());

        val header = new HttpHeaders();
        header.add("Location", "/api/v1/beer/" + savedDto.getId().toString());

        return new ResponseEntity(header, HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> updateBeer(@NotNull @PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto) {

        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@NotNull @PathVariable("beerId") UUID beerId) {

        beerService.deleteBeer(beerId);
    }

}
