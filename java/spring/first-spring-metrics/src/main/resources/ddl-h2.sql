CREATE TABLE xxx_cache (

  `id`          CHAR(24),
  `cahce_name`  VARCHAR(128),
  `key`         VARCHAR(1024),
  `value`       BINARY(16777216), -- 16*1024*1024
  `created_at`  TIMESTAMP,
  `updated_at`  TIMESTAMP,
  `ttl`         INT DEFAULT 0
)