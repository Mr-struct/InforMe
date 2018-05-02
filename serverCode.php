<?php

//initialise la connexion avec la base de donnee
  $servername = "localhost";
  $username = "********";
  $password = "********";
  $dbname = "*********";

  //cree une connexion
    try {
    $conn = new PDO('mysql:host=localhost;dbname='.$dbname.';charset=utf8', $username, $password);

  } catch(Exception $e) {

          die('Erreur : '.$e->getMessage());
  }

  $res = $_POST['dot_value']; // recupere la valeur envoyé par l application

  //execute la requete
  $reponse = $conn->query("SELECT WebPage FROM `*****` WHERE dot_value = '".$res."' ");


  //affiche la reponse de la requete
  while ($donnees = $reponse->fetch()) {

	   echo $donnees['WebPage'];

   }
   //ferme la connexion
   $reponse->closeCursor();


?>
