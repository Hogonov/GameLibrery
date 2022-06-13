-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Июн 19 2020 г., 08:30
-- Версия сервера: 10.4.11-MariaDB
-- Версия PHP: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `game_library`
--

-- --------------------------------------------------------

--
-- Структура таблицы `auto_user`
--

CREATE TABLE `auto_user` (
  `autoUserId` bigint(20) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRST_NAME` varchar(255) DEFAULT NULL,
  `LAST_NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ROLE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `auto_user`
--

INSERT INTO `auto_user` (`autoUserId`, `EMAIL`, `FIRST_NAME`, `LAST_NAME`, `PASSWORD`, `ROLE`, `USERNAME`) VALUES
(1, '1', '1', '1', '1', 'ROLE_ADMIN', '1'),
(3, 'u', 'u', 'u', 'u', 'ROLE_USER', 'u'),
(4, '', '', '', '1', 'ROLE_USER', '11'),
(5, '', '', '', '2', 'ROLE_USER', '2'),
(6, 'user', 'user', 'user', 'pass', 'ROLE_USER', 'user');

-- --------------------------------------------------------

--
-- Структура таблицы `developers`
--

CREATE TABLE `developers` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `developers`
--

INSERT INTO `developers` (`id`, `name`, `rank`, `year`) VALUES
(1, 'Father Gyeny', 5, 2018),
(2, 'EA', 4, 12),
(3, '1', 5, 1),
(4, 'Scarface', 3, 2018);

-- --------------------------------------------------------

--
-- Структура таблицы `developers_review`
--

CREATE TABLE `developers_review` (
  `id` bigint(20) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `dev_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `developers_review`
--

INSERT INTO `developers_review` (`id`, `rank`, `user_id`, `dev_id`) VALUES
(1, 4, 1, 1),
(2, 5, 3, 1),
(3, 3, 3, 4),
(4, 5, 3, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `games`
--

CREATE TABLE `games` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `developer` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `rank` double DEFAULT NULL,
  `year` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `games`
--

INSERT INTO `games` (`id`, `description`, `developer`, `genre`, `name`, `rank`, `year`) VALUES
(1, 'Vary GOOD GAME', 'Father Gyeny', 'Action', 'Boris', 3.5, 2018),
(2, 'dwa', 'Father Gyeny', 'Action', 'Adventure', 3.5, 2018),
(3, '1', 'Father Gyeny', 'Action', '1', 5, 1),
(4, '2', 'Father Gyeny', 'Action', '2', 5, 2),
(5, '3', 'Father Gyeny', 'Action', '3', 3.5, 3),
(6, '4', 'Father Gyeny', 'Action', '4', 3.5, 4),
(7, '5', 'Father Gyeny', 'Action', '5', 3.5, 5),
(8, '6', 'Father Gyeny', 'Action', '6', 3.5, 6),
(9, '7', 'Father Gyeny', 'Action', '7', 3.5, 7),
(10, '8', 'Father Gyeny', 'Action', '8', 3.5, 8),
(11, '9', 'Father Gyeny', 'Action', '9', 3.5, 9),
(12, '10', 'Father Gyeny', 'Action', '10', 3.5, 10),
(13, '11', 'Father Gyeny', 'Action', '11', 3.5, 11),
(14, '12', 'Father Gyeny', 'Action', '12', 3.5, 12),
(15, '13', 'Father Gyeny', 'Action', '13', 3.5, 13),
(16, '14', 'Father Gyeny', 'Action', '14', 3.5, 14),
(17, '15', 'Father Gyeny', 'Action', '15', 1, 15),
(18, '16', 'Father Gyeny', 'Action', '16', 3.5, 16),
(19, '17', 'Father Gyeny', 'Action', '17', 1, 17),
(20, '19', 'Father Gyeny', 'Action', '18', 3.5, 18),
(21, '19', 'Father Gyeny', 'Action', '19', 1, 19),
(22, '20', 'Father Gyeny', 'Action', '20', 3.5, 20),
(23, '21', 'Father Gyeny', 'Action', '21', 1, 21),
(24, '22', 'Father Gyeny', 'Action', '22', 3.5, 22),
(25, '23', 'Father Gyeny', 'Action', '23', 3.5, 23);

-- --------------------------------------------------------

--
-- Структура таблицы `games_developers`
--

CREATE TABLE `games_developers` (
  `game_id` bigint(20) NOT NULL,
  `developer_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `games_developers`
--

INSERT INTO `games_developers` (`game_id`, `developer_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `games_review`
--

CREATE TABLE `games_review` (
  `id` bigint(20) NOT NULL,
  `game_id` bigint(20) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `games_review`
--

INSERT INTO `games_review` (`id`, `game_id`, `rank`, `user_id`) VALUES
(1, 1, 3, 3),
(4, 2, 3, 1),
(6, 1, 4, 1),
(7, 3, 5, 1),
(8, 4, 5, 3),
(9, 3, 5, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `game_genre`
--

CREATE TABLE `game_genre` (
  `id` bigint(20) NOT NULL,
  `game_id` bigint(20) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `game_genre`
--

INSERT INTO `game_genre` (`id`, `game_id`, `genre`) VALUES
(1, NULL, 'Action'),
(2, NULL, 'GG');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `auto_user`
--
ALTER TABLE `auto_user`
  ADD PRIMARY KEY (`autoUserId`);

--
-- Индексы таблицы `developers`
--
ALTER TABLE `developers`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `developers_review`
--
ALTER TABLE `developers_review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `UK_dl2fey5upcafnhrpypshudg8t` (`rank`) USING BTREE,
  ADD KEY `FK4k22qha4r77qcxi7cfuqnpfwm` (`dev_id`);

--
-- Индексы таблицы `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `games_developers`
--
ALTER TABLE `games_developers`
  ADD PRIMARY KEY (`developer_id`,`game_id`),
  ADD UNIQUE KEY `UK_3qpfdo4c738lx8ojblh1my6qq` (`game_id`);

--
-- Индексы таблицы `games_review`
--
ALTER TABLE `games_review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `UK_2nuq01sgfw79equvdj6wuxbbs` (`rank`) USING BTREE,
  ADD KEY `FKm2g7ou5si4vxc8ljq0ms0awou` (`user_id`),
  ADD KEY `FKoc60deyxh2iaptf5igsnbajaf` (`game_id`);

--
-- Индексы таблицы `game_genre`
--
ALTER TABLE `game_genre`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `auto_user`
--
ALTER TABLE `auto_user`
  MODIFY `autoUserId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `developers`
--
ALTER TABLE `developers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `developers_review`
--
ALTER TABLE `developers_review`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `games`
--
ALTER TABLE `games`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT для таблицы `games_review`
--
ALTER TABLE `games_review`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `game_genre`
--
ALTER TABLE `game_genre`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
