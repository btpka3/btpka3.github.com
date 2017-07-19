package me.test.first.chanpay.api.scan;

import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

@Component
@ConfigurationProperties(prefix = "ut")
public class UtProps {

    public Scan scan;


    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    public static class Scan {

        private String gatewayUrl;
        private String mchPubKey;
        private String mchPriKey;

        public String getMchPubKey() {
            return mchPubKey;
        }

        public void setMchPubKey(String mchPubKey) {
            this.mchPubKey = mchPubKey;
        }

        public String getMchPriKey() {
            return mchPriKey;
        }

        public void setMchPriKey(String mchPriKey) {
            this.mchPriKey = mchPriKey;
        }

        public String getGatewayUrl() {
            return gatewayUrl;
        }

        public void setGatewayUrl(String gatewayUrl) {
            this.gatewayUrl = gatewayUrl;
        }
    }

}
