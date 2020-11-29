-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 15-11-2020 a las 18:20:20
-- Versión del servidor: 8.0.18
-- Versión de PHP: 7.2.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `trolleyes`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` double(10,2) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` double(10,2) NOT NULL,
  `fecha` datetime NOT NULL,
  `descuento_usuario` int(11) NOT NULL,
  `descuento_producto` int(11) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `id_factura` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime NOT NULL,
  `iva` int(11) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `pagado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `existencias` int(11) NOT NULL,
  `precio` double(10,2) NOT NULL,
  `imagen` bigint(20) NOT NULL DEFAULT '1',
  `descuento` int(11) NOT NULL,
  `id_tipoproducto` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoproducto`
--

CREATE TABLE `tipoproducto` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipousuario`
--

CREATE TABLE `tipousuario` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tipousuario`
--

INSERT INTO `tipousuario` (`id`, `nombre`) VALUES
(1, 'Administrador'),
(2, 'Cliente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `dni` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `apellido1` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `apellido2` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `login` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `descuento` int(11) NOT NULL DEFAULT '0',
  `id_tipousuario` bigint(20) NOT NULL DEFAULT '2',
  `token` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `validado` tinyint(1) NOT NULL DEFAULT '0',
  `activo` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `dni`, `nombre`, `apellido1`, `apellido2`, `login`, `password`, `email`, `descuento`, `id_tipousuario`, `token`, `validado`, `activo`) VALUES
(1, '12359854R', 'Admin', 'Administrador', 'Administrador', 'admin', 'da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04', 'admin@trolleyes.com', 0, 1, NULL, 1, 1),
(2, '14785236Y', 'Carla', 'Casitos', 'Ricos', 'carla', 'da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04', 'carla@trolleyes.com', 0, 2, NULL, 1, 1),
(3, '24552998Z', 'Fernando', 'Mestica', 'Leones', 'fernando', 'da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04', 'fernando@trolleyes.com', 0, 2, NULL, 1, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;



--
-- Estructura de tabla para la tabla `file`
--

CREATE TABLE `file` (
  `id` bigint(11) NOT NULL,
  `name` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `file` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Volcado de datos para la tabla `file`
--

INSERT INTO `file` (`id`, `name`, `type`, `file`) VALUES
(1, 'no-photo.jpg', 'image/jpeg', 0xffd8ffe000104a46494600010200006400640000ffec00114475636b79000100040000004b0000ffee000e41646f62650064c000000001ffdb00840003020202020203020203050303030505040303040506050505050506080607070707060808090a0a0a09080c0c0c0c0c0c0e0e0e0e0e10101010101010101010010304040606060c08080c120e0c0e1214101010101411101010101011111010101010101110101010101010101010101010101010101010101010101010101010ffc000110800e1012c03011100021101031101ffc401a20000000701010101010000000000000000040503020601000708090a0b0100020203010101010100000000000000010002030405060708090a0b1000020103030204020607030402060273010203110400052112314151061361227181143291a10715b14223c152d1e1331662f0247282f12543345392a2b26373c235442793a3b33617546474c3d2e2082683090a181984944546a4b456d355281af2e3f3c4d4e4f465758595a5b5c5d5e5f566768696a6b6c6d6e6f637475767778797a7b7c7d7e7f738485868788898a8b8c8d8e8f82939495969798999a9b9c9d9e9f92a3a4a5a6a7a8a9aaabacadaeafa110002020102030505040506040803036d0100021103042112314105511361220671819132a1b1f014c1d1e1234215526272f1332434438216925325a263b2c20773d235e2448317549308090a18192636451a2764745537f2a3b3c32829d3e3f38494a4b4c4d4e4f465758595a5b5c5d5e5f5465666768696a6b6c6d6e6f6475767778797a7b7c7d7e7f738485868788898a8b8c8d8e8f839495969798999a9b9c9d9e9f92a3a4a5a6a7a8a9aaabacadaeafaffda000c03010002110311003f00faa78abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55467bcb4b5ff007a6648bd99803f8e2a8097ccfa2c669f58e67c1558fe34a62aa27cdfa38340643ee13fa9c5572f9b7466eb232fcd0ff0ae2a8a835cd22e368ee92a7a063c0ffc35315472b2b00ca4107a11b8c55bc55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55a240153b01d4e2a926a3e6cb0b4ac76dfe9320dbe134407fd6eff462ac7ae7cc1acea4de946e503748a00413f7558fdf8ab76fe58d66ebe378c441bf6a56a1fb854fe18aa611792253fdfdd2afb2216fd6462aae3c936dfb574e7e4a062ab1fc91191fbbbb20fba03fa88c55093f933514158648e5f6a953f88a7e38aa01a0d73456e7c65b703ab29aa1f9915538aa6761e72b88c84d42312aff00bf13e16fbba1fc31564d63a9596a31fa969287a7da5e8c3e60ef8aa2715762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762a85bfd46d34d84cf74fc47eca8dd98f8018ab0cd4f5cd435997eaf1029131a25ba5496f9d3738aa61a5f93de4026d4d8a0ea214a72fa4f6c55935a58da58a7a76912c63bd06e7e67a9c555f15762aec55d8abb156880450ee0f518aa51a8f9634dbeabc6bf5794fedc63627dd7a62ac5ef34cd53429c4c09500fc171193c7e47c3e47154fb45f354773c6db5122394ecb2f456f9f81fc3156458abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec5507aa6a76fa55b1b89b763b471f766f0c5584ff00b92f31ea1fceedf4246bfc00c5598e91a25a6931feec73988f8e62373ec3c062a98e2aec55d8abb15762aec55d8abb15762ab648d25468e450cac28ca454118ab01f305a69f677e61b072c3fdd91f508de00e2a99f96fcc46365d3efdea8768656fd9f053ede18ab2dc55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8aa95cdc436903dccedc638c558e2ac0eeae2f3cc3a98082a5cf18a3ec8bfe7b9c559a695a5c1a55b0821dd8ef2c9dd9bfa7862a8dc55d8abb1558f34319e3248aa7ad1881faf155bf5ab5ff7f27fc10feb8abbeb56bfefe4ff00821fd71577d6ad7fdfc9ff00043fae2aefad5aff00bf93fe087f5c555159580653507a11b8c55bc558ef98fcc62d43585835663b4b28fd8f61effab1549343d0e6d5a6f565aadba9fde49dd8f80f7c5517e64f2f2d90faf58ad20d8491f5e07c47b1c551fe55d6cdcc634eba6acb18fdcb1fda51dbe63156478abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8ab0ff37ea8669c69b09f822f8a5a777ec3e818aa69e57d2058da0bb997f7f38aefd553b0fa7a9c553cc552bbdd4ae2dee5e240bc5694a835dc03e38aafb0d427b99fd3902d284ec3fb71546cf3241134afd17f1f6c558f4d33cf2b4afd5bf0f6c55662aec55d8abb154cb49bce27eab21d8ff767dfc31541f98fcc62d43585835663b4b28fd8f61eff00ab1549343d0e6d5a6f565aadba9fde49dd8f80f7c559d430c56f12c102844414551d00c55b9234951a391432b02194f420e2ac0754b29f42d53f724a807d4b77f6af4fa3a1c559b69b7d1ea36515dc7b731f12ff002b0d88fbf154562aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8aa1f50bb5b1b29aedffdd6a481e27a01f49c558468764fab6ac0cff1282669c9efbd69f49c559fe2a95dfdfdcdbdcb471901401d457a8c5506d7d2bb16744627a928a4e2ae4bf9a33ca35453e21003f862ab9f52b89071902b0eb46507f5e2ab3eb6dfefb8ff00e45aff004c55df5b6ff7dc7ff22d7fa62aefadb7fbee3ff916bfd31577d6dbfdf71ffc8b5fe98abbeb6dfefb8ffe45aff4c552cd4f5d78c1b7b658c49d1a4545057e469d7154b2c2c5ef1f9bd4460fc4ddc9f018ab2886fe7b78960802a220a2a851403155ff00a5af3f987dc3154d6ca579ad92593766ad7ef23154bbccfa77d7b4d6910565b7ac89e247ed0fbb1549fc9b7fe9dcc9a7b9f8661ce3ff005d7afde3f562acc315762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec558df9d2eca5b4166a77958bb7c97a7e271554f2759886c1eed87c570d407fc94dbf5d7156418aa45aaffbdadf25fd58aa1315762aec557471bcac12352cc7a018aa630e8c48adc3d3fc95fea715440d26cc0a518fbd7155297468c8ac2e54f836e315635abdeb5a48f67130322eceea6a17d87be2a9758583de3f37a88c1f89bc7d862a9f2224681107155d8018aaec55d8aa7da6ff00bc517d3face2a892011438abcfa756d175c6e1b0b794328f1426a07d2a7157a0ab0650ca6a08a83ec7156f15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762ac1fcdf37abab98fb428ab4f9fc5fc71565da641f56d3ada0e8523507e74a9fc7154562a916abfef6b7c97f562a84c55d8ab688d2384415663403154feced12d23e23773f6dbc71552bad521b725107a8e3ad3a0fa715419d66e6bb2a53c287fae2a976ade6a91616b5b51c263b3ca0d788f6f7c552cd0f439b569bd596ab6ea7f7927763e03df154e6e2cfea2fe828a201f053c31553c55d8abb154fb4dff78a2fa7f59c55158ab0cf39c1c3518a71d258e87e6a48fd4462ac93439cdc6916b21dcf00a4fba7c3fc31547e2aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55e7fad7eff5eb853fb5204fba8b8abd0315762a916abfef6b7c97f562a84c55d8aa61a3421a5798fec0a2fcce2a8cd4ee8dbc1c50d1e4d81f01dce2a91e2a96ea5a97a55b7b73f1f4661fb3ec3df1540d8583de3f37a88c1f89bc7d862accf4499523fa981454158c780ee31557d5a1125b7a9de335fa0ec71549715762aec553ed37fde28be9fd67154562ac63cee80c569278175fbc03fc31546f949cb68c83f91dc7e35fe38aa758abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb1579fea5f0f982627b4f5ff0086ae2af40c55d8aa45aaff00bdadf25fd58aa1315762a9be8b4f424f1e5fc31551d6abeac7e1c4d3efc558e6a5a97a55b7b73f1f4661fb3ec3df1540d8583de3f37a88c1f89bc7d862a9f2224681107155d8018aa374bafd7529fe557ee38aa6d7d4faa4b5fe538ab1ec55d8abb154fb4dff0078a2fa7f59c55158ab1af3b11f56b51e2edfab1544f93c5348f9c8ff00c3154f315762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762ac07ccd19875b9c8db970753f351fc7156770c82689255e8ea187d22b8aafc5522d57fded6f92feac55098abb154c746942c8f09fda00afcc62a9779ab588548b2b53ca64afa920e8b5edf3fd58ab1fb0b07bc7e6f51183f1378fb0c553e4448d0220e2abb003155d8aa69a3db11cae5c52a38a7f138aabead288ed4a7790803e43738aa498abb15762a9f69bfef145f4feb38aa2b15627e7796b25a403f643b9fa4803f562a9bf9622f4f45b7af57e4c7e9634fc3154d715762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762ac43ceb6e56eadee80da44287e686bfc7154f3cb7722eb4780d7e28c7a4dedc361f853154cf1548b55ff007b5be4bfab154262aec552ebfd55addbd2b46a4a3ed483f67e5ef8aa9685a249abce5e4256043fbc7eec7c062acb65d16df805b5fdd71140bfb38aa14e8f775a554fbd7fb31544dbe8eaa435c372a7ec0e9f7e2a8f778e18cb310a8a3e818aa437b746ea62fd146c83db1550c55d8abb154fb4dff78a2fa7f59c55158ab03f33dc7d6b59911371105897e6373f8938ab36b283eab690db7fbe9154fcc0a1c555b15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762a94f99ecbeb9a4c85455e0fdeafc87dafc31549bc997c23b896c1ced28e718ff00297a8fa47eac5597e2a916abfef6b7c97f562a84c552dd4b52f4ab6f6e7e3e8cc3f67d87be2a81b0b07bc7e6f51183f1378fb0c5590dbb35a71fabfc1c76007862a9a43acad00b84a1fe65fe98aa206a964457d4a7b716fe98aa94bac40bb44a5cfbec3154b6e6f27ba3590ec3a28e831551c55d8abb15762a9f69bfef145f4feb38aafbebb4b1b496ee4e91a934f13d87d2715611a05b3ea5aca3c9f105633ca7e46bf89c559f62aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8ab440604115076231579fea36d3685abfeeb6f4d8490378ad6a3fa1c559cd8de457f6b1ddc27e190569dc1ee0fc8e2a84bed3ae2e2e0cb195a103a9df61f2c558e6b3786cddace270d20da465350bedf3c550ba468775aab191471854fc5236d53e03156489a25c46811382aaec0027fa62abbf43ddf8afde7fa62aefd0f77e2bf79fe98abbf43ddf8afde7fa62aefd0f77e2bf79fe98abbf43ddf8afde7fa62aefd0f77e2bf79fe98abbf43ddf8afde7fa62aefd0f77e2bf79fe98abbf43ddf8afde7fa62a9a59c2d05b244f4e4b5ad3a75ae2ac63ce1aa091d74c84d421e7311fcdd87d1d7154c3ca7a71b4b037520a4973461ec83ecfdfd7154f715762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8aa51e63d23f49d9f28456e21ab47fe50eebf4e2ac7bcb7ac9d36e4dadc9a5bca7e2afec3f4aff005c5535f31798c5b06b1b06acc769251fb1ec3dff00562a92e87a1cdab4deacb55b753fbc93bb1f01ef8ab3a8618ade258205088828aa3a018aafc55d8abb15762aec55d8abb15762aec55d8aa5dadead1e936864d8ccf510a789f13ec315627a1e992eb5a8196e2ad129e770e7f68935a7cce2acf0000000500e83156f15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb1562be68d04d5f54b35f7b88c7fc487f1c558fe9d0dacf7b1457b27a50b1f89ff00857b57c7157a3410c36f0a4302848d0511474a62aa98abb15762aec55d8abb15762aec55d8abb15416a9aadb69507ab39ab1feee21f698ff004f7c558501a8798f52fe677ff818d07f018ab38d3ac20d36d52d601b2eecddd98f527154562aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8ab15d7bcae6ad79a5a7bc96e3f5aff4c5503a37992e74d22dae8196dc6dc7f6d3e55fd58ab30b3beb5bf8bd6b49048bde9d47b11d462a88c55d8abb15762aec55d8abb15762a91eafe68b4b10d0da113cfd36fb0a7dcf7f90c558d5bdaea7e62bc2e49763fde4adf6507f9f618ab35d334bb5d2a0f46dc558ff007921fb4c7df154662aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762a94eade5cb3d4eb2afee673feec51b37fac3be2ac52e74ed634297d6a3201d278c92a7e647ea38aa6563e739e301350884a3fdf89f0b7d23a1fc3154f2dbcc7a3dd014b811b7f2cbf07e276fc71547c73c330ac322b8f1520feac555315689a6e715434faa69d6e2b35cc6b4edc813f70df154aaf3ce3a7c20ada234edd8fd85fbceff862a905e6b9ab6aefe829215f610420eff3a6e71547e97e509e5225d4cfa49d7d153573f33d062acaadeda0b48560b6411c6bd1462aab8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762ad1018104541ea0e2a955ef96349bcab08fd073fb517c3f874c5526b9f255d2926d6749078382a7f0a8c5501279675b84d45bf2a7464753fc6b8aadfd19e614d84330edb13fc0e2aefd07af4d40d6f21ff5881face2a8887ca1abca7f781211df9357fe235c5534b4f25daa10d79334a7ba20e23efdce2a9e5a585958af1b48562f1206e7e64ee7154462aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aec55d8abb15762aff00ffd9);


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `file`
--
ALTER TABLE `file`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT;
COMMIT;