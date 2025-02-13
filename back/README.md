# Deux profils de lancement
- dev
- prod

Pour les modifier : aller dans application.yml (ctrl + maj + n) et modifier le champ spring.profiles.active

Mettre soit **dev** soit **prod**

Dev utilise la base de données docker-isé en local
Prod utilise la base de données en prod


Les données sont stockées en SQL et régénérées si besoin au re-lancement de la base de données.

# Pour lancer le projet

- Avoir lancé si en mode DEV la base de données via docker
- Lancer dans intellij la configuration de lancement sauvegardée TemplateApplication
- Et voilà, vous avez accès à la doc du back dans swagger via, si en local, localhost:8081/swagger-ui.html