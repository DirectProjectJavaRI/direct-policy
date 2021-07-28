package org.nhindirect.policy.mock;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("cps")
public class MockCPSResource
{

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)        
    public Mono<String> getCPS() 
    {
    	return Mono.just("Hello");
    }
}

