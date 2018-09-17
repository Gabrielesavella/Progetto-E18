use smistamento_posti;
SET FOREIGN_KEY_CHECKS=0;
unlock tables;
#LOCK TABLES `tavoli` WRITE;
DROP TABLE IF EXISTS `tavoli`;
CREATE TABLE `tavoli` (
  `ID_Locale` varchar(60) NOT NULL,
  `ID_Tavolo` varchar(60) NOT NULL,
  `numeroPosti` int(11) NOT NULL,
  PRIMARY KEY (`ID_Locale`,`ID_Tavolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `tavoli` WRITE;
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav1',4);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav2',4);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav3',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav4',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav5',8);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav6',8);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav7',4);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav8',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Fortezza della Solitudine','idtav9',8);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo A',4);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo B',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo C',8);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo D',10);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo E',4);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo F',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo G',8);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo H',10);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo I',4);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo L',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo M',8);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('La Morte Nera','Tavolo N',10);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Tempio Maledetto','Gelsomino',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Tempio Maledetto','Rosa',8);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Tempio Maledetto','Viola',10);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Tempio Maledetto','Girasole',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Tempio Maledetto','BiancoSpino',4);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Tempio Maledetto','Dente Di Leone',6);
INSERT INTO `tavoli` (`ID_Locale`,`ID_Tavolo`,`numeroPosti`) VALUES ('Tempio Maledetto','Giglio',6);
UNLOCK TABLES;
/*LOCK TABLES `specifica_tavolo` WRITE;*/
DROP TABLE IF EXISTS `specifica_tavolo`;
CREATE TABLE `specifica_tavolo` (
  `ID_Evento` varchar(60) NOT NULL,
  `ID_Invitato` varchar(60) NOT NULL,
  `TavoloD'Onore` tinyint(4) NOT NULL,
  `DifficoltaMotorie` tinyint(4) NOT NULL,
  `Vegetariano` tinyint(4) NOT NULL,
  `VicinoTV` tinyint(4) NOT NULL,
  `Bambini` tinyint(4) NOT NULL,
  `TavoloIsolato` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID_Evento`,`ID_Invitato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `specifica_tavolo` WRITE;
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Cena con Imperatore','Obiwan002',1,0,1,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','AlbSil',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','AlessandroBorghese',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','AlfPenn',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Aquaman',0,0,0,1,0,1);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Batgirl',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Batman',1,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','CapitanAmerica',0,1,1,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Catwoman',1,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Ciclope',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','EmmaWatson',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Fenice',0,1,1,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Flash',1,0,1,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','FrancescoGiuseppe',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','FrodoB',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Gandalf',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','GinWea',0,0,0,0,1,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','GordonRamsey',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','HarPot',0,0,0,0,1,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','HerGra',0,0,0,0,1,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','InvisibleGirl',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','LanternaVerde',1,0,1,1,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Legolas',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','LunLov',0,0,0,0,1,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','MinMcGra',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','MrFantastic',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','NevPac',0,0,0,0,1,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','PrincipessaPeach',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','PrincipessaSissi',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','RemLup',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Robin',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','RonWea',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Saruman',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Scarlet',0,1,0,0,0,1);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','SevPit',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','SuperLuigi',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Superman',1,0,1,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','SuperMario',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','VittSgarbi',0,0,0,0,0,0);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','Wolverine',0,0,0,1,0,1);
INSERT INTO `specifica_tavolo` (`ID_Evento`,`ID_Invitato`,`TavoloD'Onore`,`DifficoltaMotorie`,`Vegetariano`,`VicinoTV`,`Bambini`,`TavoloIsolato`) VALUES ('Matrimonio Batman-Catwoman','WonderWoman',1,0,0,0,0,1);
UNLOCK TABLES;
#LOCK TABLES `preferenza_invitato` WRITE;
DROP TABLE IF EXISTS `preferenza_invitato`;
CREATE TABLE `preferenza_invitato` (
  `ID_Evento` varchar(60) NOT NULL,
  `ID_Invitato` varchar(60) NOT NULL,
  `VoglioStareVicinoA` varchar(300) DEFAULT NULL,
  `NonVoglioStareVicinoA` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID_Invitato`,`ID_Evento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `preferenza_invitato` WRITE;
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','AlbSil',NULL,NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','AlessandroBorghese','Gandalf',NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','AlfPenn','Robin Batgirl','SevPit');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Aquaman','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Batgirl','Robin','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Batman','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','CapitanAmerica','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Catwoman','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Ciclope','Fenice','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','EmmaWatson','Legolas','SevPit');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Fenice','Ciclope','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Flash','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','FrancescoGiuseppe','PrincipessaSissi ',NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','FrodoB','Gandalf','Saruman');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Gandalf','FrodoB','Saruman');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','GinWea','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','GordonRamsey','SuperMario','VittSgarbi');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','HarPot','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','HerGra','RonWea','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','InvisibleGirl','Mr Fantastic','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','LanternaVerde','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Legolas','FrodoB','Saruman');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','LunLov',NULL,NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','MinMcGra',NULL,NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','MrFantastic','Invisible Girl',NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','NevPac','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Cena con Imperatore','Obiwan002','LukSky003, LeiMor004','ImpPal005');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','PrincipessaPeach','SuperMario','VittSgarbi');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','PrincipessaSissi','FrancescoGiuseppe AlessandroBorghese',NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','RemLup',NULL,NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Robin','Batgirl','SuperMario');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','RonWea','HerGra','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Saruman',NULL,'Gandalf');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Scarlet','','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','SevPit','',NULL);
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','SuperLuigi','SuperMario','VittSgarbi');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Superman','Batman WonderWoman','Aquaman');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','SuperMario','PrincipessaPeach','VittSgarbi');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','VittSgarbi','EmmaWatson','VittSgarbi');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','Wolverine','Fenice','');
INSERT INTO `preferenza_invitato` (`ID_Evento`,`ID_Invitato`,`VoglioStareVicinoA`,`NonVoglioStareVicinoA`) VALUES ('Matrimonio Batman-Catwoman','WonderWoman','','');
UNLOCK TABLES;
#LOCK TABLES `locali` WRITE;
DROP TABLE IF EXISTS `locali`;
CREATE TABLE `locali` (
  `ID_Locale` varchar(60) NOT NULL,
  `numMaxtavoli` int(11) NOT NULL,
  `oraApertura` varchar(45) NOT NULL,
  `oraChiusura` varchar(45) NOT NULL,
  `giornoChiusura` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_Locale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `locali` WRITE;
INSERT INTO `locali` (`ID_Locale`,`numMaxtavoli`,`oraApertura`,`oraChiusura`,`giornoChiusura`) VALUES ('Fortezza della Solitudine',42,'00:01','23:59','Lunedì');
INSERT INTO `locali` (`ID_Locale`,`numMaxtavoli`,`oraApertura`,`oraChiusura`,`giornoChiusura`) VALUES ('La Morte Nera',77,'09:00','02:00','Lunedì');
INSERT INTO `locali` (`ID_Locale`,`numMaxtavoli`,`oraApertura`,`oraChiusura`,`giornoChiusura`) VALUES ('Tempio Maledetto',33,'09:00','17:00','Giovedì');
UNLOCK TABLES;
#LOCK TABLES `invitati` WRITE;
DROP TABLE IF EXISTS `invitati`;
CREATE TABLE `invitati` (
  `ID_Evento` varchar(60) NOT NULL,
  `ID_Invitato` varchar(60) NOT NULL,
  `NomeInvitato` varchar(60) NOT NULL,
  `CognomeInvitato` varchar(60) NOT NULL,
  `EtaInvitato` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_Evento`,`ID_Invitato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `invitati` WRITE;
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Cena con Imperatore','Obiwan002','ObiWan','Kenobi',50);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','AlbSil','Albus','Silente',111);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','AlessandroBorghese','Voto','Dieci',40);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','AlfPenn','Alfred','Pennyworth',60);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Aquaman','Arthur','Curry',40);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Batgirl','Barbara','Gordon',25);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Batman','Bruce','Wayne',40);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','CapitanAmerica','Steve','Rogers',40);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Catwoman','Selina','Kyle',28);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Ciclope','Scott','Summers',33);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','EmmaWatson','Emma','Watson',30);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Fenice','Jean','Grey',32);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Flash','Bart','Allen',35);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','FrancescoGiuseppe','Francesco','Giuseppe',40);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','FrodoB','Frodo','Beggins',15);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Gandalf','Mago','Bianco',100);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','GinWea','Ginny','Weasley',16);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','GordonRamsey','Gordon','Ramsey',40);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','HarPot','Harry','Potter',17);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','HerGra','Hermione','Granger',17);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','InvisibleGirl','Susan','Storm',29);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','LanternaVerde','Hal','Jordan',45);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Legolas','Lego','Las',30);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','LunLov','Luna','Lovegood',17);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','MinMcGra','Minerva','McGranitt',60);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','MrFantastic','Reed','Richards',37);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','NevPac','Neville','Paciock',17);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','PrincipessaPeach','Principessa','Peach',30);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','PrincipessaSissi','Principessa','Sissi',35);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','RemLup','Remus','Lupin',50);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Robin','Dick','Grayson',30);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','RonWea','Ron','Weasley',17);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Saruman','Mago','Nero',120);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Scarlet','Wanda','Maxinoff',30);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','SevPit','Severus','Piton',50);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','SuperLuigi','Luigi','Bros',45);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Superman','Clark','Kent',30);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','SuperMario','Mario','Bros',40);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','VittSgarbi','Vittorio','Sgarbi',60);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','Wolverine','Logan','Howlett',50);
INSERT INTO `invitati` (`ID_Evento`,`ID_Invitato`,`NomeInvitato`,`CognomeInvitato`,`EtaInvitato`) VALUES ('Matrimonio Batman-Catwoman','WonderWoman','Diana ','Prince',45);
UNLOCK TABLES;
#LOCK TABLES `eventi` WRITE;
DROP TABLE IF EXISTS `eventi`;
CREATE TABLE `eventi` (
  `ID_Evento` varchar(60) NOT NULL,
  `ID_Cliente` varchar(60) NOT NULL,
  `DataEvento` varchar(45) NOT NULL,
  `ID_Locale` varchar(60) NOT NULL,
  `NumeroInvitati` int(3) NOT NULL,
  PRIMARY KEY (`ID_Evento`,`ID_Cliente`,`ID_Locale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `eventi` WRITE;
INSERT INTO `eventi` (`ID_Evento`,`ID_Cliente`,`DataEvento`,`ID_Locale`,`NumeroInvitati`) VALUES ('Bevute col Santo Graal','IndJon81','19/06/2019','Tempio Maledetto',100);
INSERT INTO `eventi` (`ID_Evento`,`ID_Cliente`,`DataEvento`,`ID_Locale`,`NumeroInvitati`) VALUES ('Cena con Imperatore','DarVad77','21-01-2021','La Morte Nera',300);
INSERT INTO `eventi` (`ID_Evento`,`ID_Cliente`,`DataEvento`,`ID_Locale`,`NumeroInvitati`) VALUES ('Matrimonio Batman-Catwoman','BruWay39','21/01/2019','Fortezza della Solitudine',20);
INSERT INTO `eventi` (`ID_Evento`,`ID_Cliente`,`DataEvento`,`ID_Locale`,`NumeroInvitati`) VALUES ('Matrimonio Leila-Ian','LukSky977','13/02/2019','Fortezza della Solitudine',200);
UNLOCK TABLES;

#LOCK TABLES `clienti` WRITE;
DROP TABLE IF EXISTS `clienti`;
CREATE TABLE `clienti` (
  `ID_Cliente` varchar(60) NOT NULL,
  `NomeCliente` varchar(60) NOT NULL,
  `CognomeCliente` varchar(60) NOT NULL,
  `EmailCliente` varchar(60) NOT NULL,
  `PasswordCliente` varchar(60) NOT NULL,
  PRIMARY KEY (`ID_Cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Contiene i dati relativi agli user del programma';
LOCK TABLES `clienti` WRITE;
INSERT INTO `clienti` (`ID_Cliente`,`NomeCliente`,`CognomeCliente`,`EmailCliente`,`PasswordCliente`) VALUES ('marco','marco','marco','marco@gmail.com','marco');
INSERT INTO `clienti` (`ID_Cliente`,`NomeCliente`,`CognomeCliente`,`EmailCliente`,`PasswordCliente`) VALUES ('BruWay39','Bruce','Wayne','cavaliereoscuro@gmail.com','Gotham');
INSERT INTO `clienti` (`ID_Cliente`,`NomeCliente`,`CognomeCliente`,`EmailCliente`,`PasswordCliente`) VALUES ('DarVad77','Darth','Vader','anakynskywalker@gmail.com','Padme');
INSERT INTO `clienti` (`ID_Cliente`,`NomeCliente`,`CognomeCliente`,`EmailCliente`,`PasswordCliente`) VALUES ('IndJon81','Henry Jr','Jones','indianajones@gmail.com','Serpenti');
INSERT INTO `clienti` (`ID_Cliente`,`NomeCliente`,`CognomeCliente`,`EmailCliente`,`PasswordCliente`) VALUES ('LukSky977','Luke','Skywalker','lukeskywalker@gmail.com','Force');
UNLOCK TABLES;
#LOCK TABLES `agenda` WRITE;
DROP TABLE IF EXISTS `agenda`;
CREATE TABLE `agenda` (
  `ID_locale` varchar(60) NOT NULL,
  `data` varchar(60) NOT NULL,
  `id_tavoli` varchar(600) DEFAULT NULL,
  PRIMARY KEY (`ID_locale`,`data`),
  CONSTRAINT `FK_ID_locale` FOREIGN KEY (`ID_locale`) REFERENCES locali(`ID_Locale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `agenda` WRITE;
LOCK TABLES `clienti` WRITE;
LOCK TABLES `invitati` WRITE;
LOCK TABLES `locali` WRITE;
LOCK TABLES `preferenza_invitato` WRITE;
LOCK TABLES `specifica_tavolo` WRITE;
LOCK TABLES `tavoli` WRITE;
unlock tables;
SET FOREIGN_KEY_CHECKS=1;