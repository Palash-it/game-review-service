-- games definition

CREATE TABLE `games`
(
    `game_id`     bigint       NOT NULL AUTO_INCREMENT,
    `title`       varchar(100) NOT NULL,
    `description` text,
    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status`      varchar(32)  NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (`game_id`),
    UNIQUE KEY `games_un` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- players definition

CREATE TABLE `players`
(
    `player_id`  bigint       NOT NULL AUTO_INCREMENT,
    `first_name` varchar(100) NOT NULL,
    `last_name`  varchar(100)          DEFAULT NULL,
    `email`      varchar(255) NOT NULL,
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status`     varchar(32)  NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (`player_id`),
    UNIQUE KEY `players_un` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

