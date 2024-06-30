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

CREATE TABLE `games_audit_log`
(
    `rev`         int not null,
    `revtype`     tinyint null,
    `game_id`     bigint NULL,
    `title`       varchar(100) NULL,
    `description` text NULL,
    `status`      varchar(32) NULL,
    `created_at`  datetime NULL,
    PRIMARY KEY (game_id, rev)
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

CREATE TABLE `players_audit_log`
(
    `rev`        int not null,
    `revtype`    tinyint null,
    `player_id`  bigint NULL,
    `first_name` varchar(100) NULL,
    `last_name`  varchar(100) NULL,
    `email`      varchar(255) NULL,
    `created_at` datetime NULL,
    `status`     varchar(32) NULL,
    PRIMARY KEY (player_id, rev)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- player_game_interaction definition

-- gamereviewdb.player_game_interaction definition

CREATE TABLE `player_game_interaction`
(
    `interaction_id`   bigint    NOT NULL AUTO_INCREMENT,
    `player_id`        bigint    NOT NULL,
    `game_id`          bigint    NOT NULL,
    `is_loved`         tinyint(1) NOT NULL,
    `created_at`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` datetime NULL,
    PRIMARY KEY (`interaction_id`),
    UNIQUE KEY `player_game_interaction_un` (`player_id`,`game_id`),
    KEY                `player_game_interaction_FK` (`game_id`),
    CONSTRAINT `player_game_interaction_FK` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`),
    CONSTRAINT `player_game_interaction_FK_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- player_game_interaction_audit_log definition

CREATE TABLE `player_game_interaction_audit_log`
(
    `rev`              int not null,
    `revtype`          tinyint null,
    `interaction_id`   bigint NULL,
    `player_id`        bigint NULL,
    `game_id`          bigint NULL,
    `is_loved`         tinyint(1) NULL,
    `created_at`       datetime NULL,
    `last_modified_at` datetime NULL,
    PRIMARY KEY (interaction_id, rev)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- revinfo definition

CREATE TABLE `revinfo`
(
    `rev`      int NOT NULL AUTO_INCREMENT,
    `revtstmp` bigint DEFAULT NULL,
    PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

