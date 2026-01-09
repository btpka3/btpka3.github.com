package me.test.first.spring.boot.test.arthas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/23
 */
public interface ArthasApi {

    ExecResp exec(String command);

    InitSessionResp initSession();

    JoinSessionResp joinSession(String sessionId);

    PullResultsResp pullResults(String sessionId, String consumerId);

    AsyncExecResp asyncExec(String sessionId, String command);

    InterruptJobResp interruptJob(String sessionId);

    CloseSessionResp closeSession(String sessionId);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    class ExecResp implements Serializable {
        private static final long serialVersionUID = 1L;
        private String state;
        private String sessionId;
        private String message;
        private Body body;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Body implements Serializable {
            private static final long serialVersionUID = 1L;
            private List<Result> results;
            private Boolean timeExpired;
            private String command;
            private String jobStatus;
            private Integer jobId;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Result implements Serializable {
            private static final long serialVersionUID = 1L;
            private String type;
            private String version;
            private Integer jobId;
            private Integer statusCode;
            /**
             * 仅当 type=="sysprop" 时，该字段才有值
             */
            private Map<String, String> props;
            /**
             * 仅当 type=="sysenv" 时，该字段才有值
             */
            private Map<String, String> env;
            /**
             * 仅当 type=="ognl" 时，该字段才有值
             */
            private String value;
            /**
             * 仅当 type=="help" 时，该字段才有值
             */
            private List<Command> commands;
             /**
             * 仅当 type=="options" 时，该字段才有值
             */
            private List<Option> options;
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Command implements Serializable {
            private static final long serialVersionUID = 1L;
            private List<String> arguments;
            private String name;
            private List<String> options;
            private String summary;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class JvmInfo implements Serializable {
            private static final long serialVersionUID = 1L;
            private List<String> arguments;
            private String name;
            private List<String> options;
            private String summary;
        }
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Option implements Serializable {
            private static final long serialVersionUID = 1L;
            private String description;
            private Integer level;
            private String name;
            private String summary;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    class InitSessionResp implements Serializable {
        private static final long serialVersionUID = 1L;
        private String sessionId;
        private String consumerId;
        private String state;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    class JoinSessionResp implements Serializable {
        private static final long serialVersionUID = 1L;
        private String sessionId;
        private String consumerId;
        private String state;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    class PullResultsResp implements Serializable {
        private static final long serialVersionUID = 1L;
        private String sessionId;
        private String consumerId;
        private String state;
        private Body body;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Body implements Serializable {
            private static final long serialVersionUID = 1L;
            private List<Result> results;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Result implements Serializable {
            private static final long serialVersionUID = 1L;
            private String inputStatus;
            private Integer jobId;
            private Integer type;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    class AsyncExecResp implements Serializable {
        private static final long serialVersionUID = 1L;
        private String sessionId;
        private String state;
        private Body body;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Body implements Serializable {
            private static final long serialVersionUID = 1L;
            private String jobStatus;
            private String jobId;
            private String command;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    class InterruptJobResp implements Serializable {
        private static final long serialVersionUID = 1L;
        private String state;
        private Body body;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(toBuilder = true)
        public static class Body implements Serializable {
            private static final long serialVersionUID = 1L;
            private String jobStatus;
            private String jobId;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    class CloseSessionResp implements Serializable {
        private static final long serialVersionUID = 1L;
        private String state;
    }


}
