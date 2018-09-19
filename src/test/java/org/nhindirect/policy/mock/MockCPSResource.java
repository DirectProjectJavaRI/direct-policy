package org.nhindirect.policy.mock;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("cps")
public class MockCPSResource
{
	protected static final CacheControl noCache;
	
	static
	{
		noCache = CacheControl.noCache();
	}

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)        
    public ResponseEntity<String> getCPS() 
    {
    	return ResponseEntity.status(HttpStatus.OK).cacheControl(noCache).body("Hello");
    }
}

