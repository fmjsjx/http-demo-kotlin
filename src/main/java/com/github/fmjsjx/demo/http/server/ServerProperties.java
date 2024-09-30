package com.github.fmjsjx.demo.http.server;

import com.github.fmjsjx.libcommon.util.RuntimeUtil;
import com.github.fmjsjx.libcommon.util.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.unit.DataSize;

import java.io.File;
import java.net.InetAddress;
import java.time.Duration;
import java.util.Map;

@ConfigurationProperties("server")
public class ServerProperties {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);
    private static final DataSize DEFAULT_MAX_CONTENT_SIZE = DataSize.ofMegabytes(1);
    private static final String DEFAULT_ACCESS_LOG_PATTERN = ":method :path :http-version :remote-addr :content-length - :status :response-time ms :result-length-humanreadable";

    private int ioThreads = RuntimeUtil.availableProcessors();

    @NestedConfigurationProperty
    private HttpProperties http = new HttpProperties();

    public int getIoThreads() {
        return ioThreads;
    }

    public void setIoThreads(int ioThreads) {
        this.ioThreads = ioThreads;
    }

    public HttpProperties getHttp() {
        return http;
    }

    public void setHttp(HttpProperties http) {
        this.http = http;
    }

    @Override
    public String toString() {
        return "ServerProperties{" +
                "ioThreads=" + getIoThreads() +
                ", http=" + getHttp() +
                '}';
    }

    public static class HttpProperties {

        private int port = 8080;
        private InetAddress address;
        private DataSize maxContentSize = DEFAULT_MAX_CONTENT_SIZE;
        private Duration timeout = DEFAULT_TIMEOUT;
        private String accessLogPattern = DEFAULT_ACCESS_LOG_PATTERN;
        private SslProperties ssl;

        public HttpProperties() {
        }

        public HttpProperties(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public InetAddress getAddress() {
            return address;
        }

        public void setAddress(InetAddress address) {
            this.address = address;
        }

        public DataSize getMaxContentSize() {
            var maxContentSize = this.maxContentSize;
            if (maxContentSize == null) {
                this.maxContentSize = maxContentSize = DEFAULT_MAX_CONTENT_SIZE;
            }
            return maxContentSize;
        }

        public void setMaxContentSize(DataSize maxContentSize) {
            this.maxContentSize = maxContentSize;
        }

        public Duration getTimeout() {
            var timeout = this.timeout;
            if (timeout == null) {
                this.timeout = timeout = DEFAULT_TIMEOUT;
            }
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            this.timeout = timeout;
        }

        public String getAccessLogPattern() {
            var accessLogPattern = this.accessLogPattern;
            if (accessLogPattern == null) {
                this.accessLogPattern = accessLogPattern = DEFAULT_ACCESS_LOG_PATTERN;
            }
            return accessLogPattern;
        }

        public void setAccessLogPattern(String accessLogPattern) {
            this.accessLogPattern = accessLogPattern;
        }

        public SslProperties getSsl() {
            return ssl;
        }

        public void setSsl(SslProperties ssl) {
            this.ssl = ssl;
        }

        public boolean sslEnabled() {
            var ssl = this.ssl;
            return ssl != null && ssl.isEnabled();
        }

        @Override
        public String toString() {
            return "HttpProperties{" +
                    "port=" + getPort() +
                    ", address=" + getAddress() +
                    ", maxContentSize=" + getMaxContentSize() +
                    ", timeout=" + getTimeout() +
                    ", accessLogPattern='" + getAccessLogPattern() + '\'' +
                    '}';
        }

    }

    public static class KeyCertProperties {

        /**
         * Path string of the key certificate chain file. The file must be an X.509
         * certificate chain file in PEM format.
         */
        protected String keyCertChainFile;
        /**
         * Path string of the key file. The file must be a PKCS#8 private key file in
         * PEM format.
         */
        protected String keyFile;
        /**
         * The password of the {@code keyFile}, or {@code null} if it's not
         * password-protected.
         */
        protected String keyPassword;

        public String getKeyCertChainFile() {
            return keyCertChainFile;
        }

        public File keyCertChainFile() {
            var keyCertChainFile = this.keyCertChainFile;
            return StringUtil.isBlank(keyCertChainFile) ? null : new File(keyCertChainFile);
        }

        public void setKeyCertChainFile(String keyCertChainFile) {
            this.keyCertChainFile = keyCertChainFile;
        }

        public String getKeyFile() {
            return keyFile;
        }

        public File keyFile() {
            var keyFile = this.keyFile;
            return StringUtil.isBlank(keyFile) ? null : new File(keyFile);
        }

        public void setKeyFile(String keyFile) {
            this.keyFile = keyFile;
        }

        public String getKeyPassword() {
            return keyPassword;
        }

        public void setKeyPassword(String keyPassword) {
            this.keyPassword = StringUtil.isEmpty(keyPassword) ? null : keyPassword;
        }

        @Override
        public String toString() {
            return "KeyCertProperties{" +
                    "keyCertChainFile='" + getKeyCertChainFile() + '\'' +
                    ", keyFile='" + getKeyFile() + '\'' +
                    ", keyPassword='" + getKeyPassword() + '\'' +
                    '}';
        }

    }

    public static class SslProperties extends KeyCertProperties {

        /**
         * Whether to enable SSL support. The default is false.
         */
        private boolean enabled;
        /**
         * SNI properties.
         */
        @NestedConfigurationProperty
        private SniProperties sni;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public SniProperties getSni() {
            return sni;
        }

        public void setSni(SniProperties sni) {
            this.sni = sni;
        }

        public boolean sniEnabled() {
            return isEnabled() && getSni() != null && getSni().isEnabled();
        }

        @Override
        public String toString() {
            return "SslProperties{" +
                    "enabled=" + isEnabled() +
                    ", sni=" + getSni() +
                    ", keyCertChainFile='" + getKeyCertChainFile() + '\'' +
                    ", keyFile='" + getKeyFile() + '\'' +
                    ", keyPassword='" + getKeyPassword() + '\'' +
                    '}';
        }

    }

    public static class SniProperties {
        /**
         * Whether to enable SNI support or not.
         * <p>
         * The default is {@code false}.
         */
        private boolean enabled;
        /**
         * The mapping of domain name to {@link KeyCertProperties}.
         */
        private Map<String, KeyCertProperties> mapping;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public Map<String, KeyCertProperties> getMapping() {
            return mapping;
        }

        public void setMapping(Map<String, KeyCertProperties> mapping) {
            this.mapping = mapping;
        }

        @Override
        public String toString() {
            return "SniProperties{" +
                    "enabled=" + isEnabled() +
                    ", mapping=" + getMapping() +
                    '}';
        }

    }

}
