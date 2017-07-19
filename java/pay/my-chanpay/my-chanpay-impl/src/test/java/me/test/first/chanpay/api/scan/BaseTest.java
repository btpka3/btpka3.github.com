package me.test.first.chanpay.api.scan;

import com.fasterxml.jackson.databind.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(["default", "ut"])
@SpringBootTest(
        classes = {UtApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public abstract class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UtProps props;

    @Autowired
    protected CpScanApi cpScanApi;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ConversionService conversionService;
    @Autowired
    protected List<Converter> converters;

}
