# Getting Started <a id="top" />

Pour voir correctement ce `.md` depuis intellij : cliquez sur l'icône `Preview` près des onglets en haut à droite. Les
liens ne fonctionneront pas correctement depuis GitLab.

- .1. [Liens utiles](#liensutiles)
    - .1.1. [Général](#liensutilesgeneral)
    - .1.2. [Local](#liensutileslocal)
    - .1.3. [CI](#liensutilesci)
    - .1.4. [REC](#liensutilesrec)
    - .1.5. [PROD](#liensutilesprod)
- .2. [Prérequis](#prerequis)
- .3. [L'application](#theapp)
    - .3.1. [Démarrer l'application](#theappstartapp)
    - .3.2. [Éteindre l'application](#theappstopapp)
    - .3.3. [Problème au redémarrage de l'application](#theapppbstartapp)
    - .3.3. [Je souhaite réinitialiser ma base de donnée](#theappresetbdd)
- .4. [Configuration utile pour IntelliJ](#configintellij)
    - .4.1. [Les imports](#configintellijimports)
    - .4.2. [Save action](#configintellijsaveaction)
    - .4.3. [S'assurer que gradle utilise bien le wrapper et le jdk du projet](#configintellijgradle)
    - .4.4. [Sonarlint](#configintellijsonarlint)
    - .4.5. [Dictionnaire français](#configintellijdico)
    - .4.6. [Correction orthographiques / grammaticales](#configintellijproofread)
    - .4.7. [Ajustement sur la mémoire consommée par l'IDE](#configintellijmemory)
- .5. [Infos utiles sur le dev](#devutile)
    - .5.1. [Flyway](#devutileflyway)
        - .5.1.1. [Pourquoi ?](#devutileflywaywhy)
        - .5.1.2. [Comment ?](#devutileflywayhow)
        - .5.1.3. [Pitfalls !](#devutileflywaypitfalls)
        - .5.1.4. [Pour finir](#devutileflywayfinish)

## 1. Liens utiles <a id="liensutiles" /> [&#9650;](#top)

### 1.1. Général <a id="liensutilesgeneral" /> [&#9650;](#top)

* Git :
    * Back : https://cdsgit.asi.fr/asi-brest-outils/ludotheque-be
    * Front :
* MRs :
    * Back : https://cdsgit.asi.fr/asi-brest-outils/ludotheque-be/-/merge_requests
    * Front :
* Jira : https://cybele-asi.atlassian.net/jira/software/projects/LUD/boards/12
* VM : 10.9.6.9

### 1.2. Local <a id="liensutileslocal" /> [&#9650;](#top)

* Back : http://localhost:8081/swagger-ui.html
* Front :
* MailDev : http://localhost:8180/#/

### 1.3. CI <a id="liensutilesci" /> [&#9650;](#top)

* Back :
* Front :

### 1.4. REC <a id="liensutilesrec" /> [&#9650;](#top)

* Back :
* Front :

### 1.5. PROD <a id="liensutilesprod" /> [&#9650;](#top)

* Back :
* Front :

## 2. Prérequis <a id="prerequis" /> [&#9650;](#top)

- Installer un IDE de votre choix, Intellij est très bien, la version community suffit.
    - Sur linux, passez par Snap (https://snapcraft.io/store).
    - Sur windows, passez par Chocolatey (https://chocolatey.org/).
    - Pas de licence étudiante après la signature d'un CDI.
- Installer docker. Attention, **ne surtout pas installer la version docker desktop** .
    - S'ajouter dans le groupe docker pour se simplifier la vie (du moins sur linux)
- Installer un jdk (actuellement un jdk 21 (la version `Temurin`, d'`eclipse`, est très bien), vérifiez
  dans `build.gradle` que c'est bien toujours la version attendue).
    - Sur linux, je vous conseille de passer par sdkman (https://sdkman.io/)
    - Sur windows par Chocolatey (https://chocolatey.org/).
    - Vous pouvez aussi passer par l'IDE pour télécharger le jdk, l'inconvénient étant qu'il est plus pénible d'accès
      hors de l'IDE
    - Dans tous les cas, après l'installation, enregistrez le sdk dans l'IDE sous le nom "21", pour aider `gradle` à le
      trouver.
    - Profitez en pour définir un JAVA_HOME vers votre jdk.
- Installer un outil de gestion de base de donnée
    - Sur linux, dbeaver est très bien (https://snapcraft.io/dbeaver-ce)
    - La version ultimate d'IntelliJ à une très bonne vue "Database"

## 3. L'application <a id="theapp" /> [&#9650;](#top)

### 3.1. Démarrer l'application <a id="theappstartapp" /> [&#9650;](#top)

- Il faut que la base de donnée tourne, et donc, que l'on ait lancé docker via le compose présent.

Depuis le dossier du projet :

```
docker compose up -d --build
```

* compose : On utilise la configuration présente dans `docker-compose.yml`
* up : on démarre l'application
* -d : en tache de fond
* --build : on doit construire l'une des images (ici, on ajoute une gestion des timezones pour MySQL)

```
./gradlew bootRun
```

* gradlew : On utilise le wrapper `gradle`, le projet gère lui-même la version de `gradle` dont il a besoin pour se
  construire
* bootRun : les taches `gradle` dépendent les unes des autres. En lançant l'application, toutes les taches dont il
  dépend seront lancées avant (assemble, etc)

### 3.2. Éteindre l'application <a id="theappstopapp" /> [&#9650;](#top)

Un Ctrl+C sur le `bootRun` devrait suffire.
Pour les conteneurs dockers, vous pouvez les laisser vivre si vous n'avez pas besoin des ressources immédiatement
(pas besoin de les éteindre, en général, ils redémarrent d'ailleurs avec la machine), sinon :

```
docker compose down
```

* compose : On utilise la configuration présente dans `docker-compose.yml`
* down : on éteint l'application

### 3.3. Problème au redémarrage de l'application <a id="theapppbstartapp" /> [&#9650;](#top)

Le `bootRun` plante, car le port est déjà occupé. Il est possible qu'un précédent `bootRun` se soit mal éteint et tourne
toujours en tache de fond.
Le paquet `net-tools`, sur linux contient un super outil pour le détecter, netstat

```
netstat -tnlp
kill -9 <PID>
```

* t : tcp
* n : affiche plutôt les adresses sous forme numérique plutôt qu'en nom de domaine
* l : seulement les ports en écoute
* p : affiche les PIDs
* PID : le PID du process qui utilisait le port de l'application (Port 8080 normalement)

### 3.4. Je souhaite réinitialiser ma base de donnée <a id="theappresetbdd" /> [&#9650;](#top)

Pour reset la bdd, le plus simple est de la supprimer et de laisser flyway la reconstruire au prochain `bootRun`.
J'utilise cette commande (ces 3 commandes chaînées) pour le faire.

```
docker compose down --remove-orphans; docker volume rm ludotheque-be_mysql-data; docker compose up -d --build
<on attends 5-10 secondes que la bdd redémarre>
./gradlew bootRun
```

* --remove-orphans : j'en profite pour tout purger. les conteneurs, les interfaces réseau virtuelles, tout.
* volume : gestion des volumes, la bdd est stockée sur l'un deux
* rm : on souhaite supprimer un volume, ici, la bdd
* `ludotheque-be_mysql-data` : le nom du volume qui contient la bdd, du moins pour l'instant.

## 4. Configuration utile pour IntelliJ <a id="configintellij" /> [&#9650;](#top)

### 4.1. Les imports <a id="configintellijimports" /> [&#9650;](#top)

Le temps que je configure une solution de lint automatique du back, on peut déjà aller retirer les jokers sur les
imports.

File -> Settings -> Editor -> Code Style -> Java -> Imports

* Class count to use import with '*': 700
* Names count to use static import with '*': 700

### 4.2. Save action <a id="configintellijsaveaction" /> [&#9650;](#top)

File -> Settings -> Tools -> Actions on save

* Reformat code
* Optimize imports
* Rearrange code
* Run code cleanup

### 4.3. S'assurer que gradle utilise bien le wrapper et le jdk du projet <a id="configintellijgradle" /> [&#9650;](#top)

File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle

* Distribution : Wrapper
* Gradle JVM : Project SDK

### 4.4. Sonarlint <a id="configintellijsonarlint" /> [&#9650;](#top)

File -> Settings -> Plugins -> Marketplace -> SonarLint -> Restart IDE
File -> Settings -> SonarLint

* Automatically trigger analysis

File -> Settings -> SonarLint -> +

* Nom : sonarqube
* Url : http://10.9.6.29:9093/ (tant que la 10.9.6.9 n'est pas configurée)
* Token : Create Token (admin / admin, allow connexion entre sonar et intellij, tant que la 10.9.6.9 n'est pas
  configurée)

Clic droit + SonarLint pour le menu contextuel.

* Vous pouvez analyser tout le projet en mettant quelques exclusions, ou analyser juste un dossier

### 4.5. Dictionnaire français <a id="configintellijdico" /> [&#9650;](#top)

File -> Settings -> Editor -> Natural Language -> + -> Français

### 4.6. Correction orthographiques / grammaticales <a id="configintellijproofread" /> [&#9650;](#top)

File -> Code -> Inspect Code -> Whole Project

Vue Problems -> Inspections on Project 'ludotheque-be' -> Inspection Results -> Proofreading

* Profitez d'avoir les langues anglaises et françaises d'installées pour pas laisser de fautes dans les commentaires /
  la doc
* Il risque d'y avoir des faux positifs, laissez la javadoc / doc swagger compréhensible, quelques faux positifs ne sont
  pas important.

### 4.7. Ajustement sur la mémoire consommée par l'IDE <a id="configintellijmemory" /> [&#9650;](#top)

File -> Settings -> Build, Execution, Deployment -> Compiler

* Shared build process heap size : 700

Help -> Change memory settings

* 2048 MiB

Help -> Edit Custom VM Options

* Pas de changements

Help -> Edit Custom Properties

* Pas de changements

## 5. Infos utiles sur le dev <a id="devutile" /> [&#9650;](#top)

### 5.1. Flyway <a id="devutileflyway" /> [&#9650;](#top)

#### 5.1.1. Pourquoi ? <a id="devutileflywaywhy" /> [&#9650;](#top)

JPA crée pour nous des tables, et ajoute pour nous des colonnes. Mais il ne supprimera rien automatiquement, ça doit
être à nous de produire un script SQL de nettoyage.
En pratique, c'est trop simple d'oublier certains de ces scripts et on se rend compte sur presque tous les projets que
les schémas de données sont différents entre les environnements.

* ça peut être une table / colonne que l'on a oublié de supprimer
* ça peut être une feature qui ajoute une table, mais que l'on n'a finalement pas livré, la table elle, est toujours
  présente
* ça peut être une contrainte sur une colonne, dont le nom a été généré automatiquement avec une portion aléatoire, et
  donc, différente entre tous les environnements.

Peu-importe la raison, pour garantir que tous les schémas de BDD sont identiques / corrects, on désactive la création /
mise à jour automatique de JPA, et on passe exclusivement par des scripts SQL.
Flyway nous permet de les gérer.

#### 5.1.2. Comment ? <a id="devutileflywayhow" /> [&#9650;](#top)

Nous avons deux dossiers :

* src/main/resources/db/migration : Les scripts qui seront joués sur tous les environnements.
* src/main/resources/db/data : Les scripts qui ne seront joués qu'en local pour le dev. Ils nous permettent de recréer
  une base déjà alimentée très simplement.

Les scripts sont forcément joués dans l'ordre alphanumérique, qu'ils soient dans l'un ou l'autre des dossiers.

Ils suivent un nommage versionné classique : `Majeur.Mineur.patch`

* Majeur : Changement cassant, suffisamment impactant pour que les différents clients de l'api doivent engager de lourds
  travaux.
* Mineur : Changement standard, nous incrémenteront principalement ce nombre
* Patch : Correctif sur le dernier changement mineur

#### 5.1.3. Pitfalls ! <a id="devutileflywaypitfalls" /> [&#9650;](#top)

Pour nous garantir l'intégrité des schémas de BDD, Flyway soit garantir que personne n'ira modifier des fichiers SQL
déjà
joués.

Sur la BDD, il crée une table flyway_schema_history, qui contient :

* script : le nom du fichier
* checksum : le checksum du script, qui garanti qu'il n'a pas été modifié

Si vous modifiez un fichier apres qu'il ait été joué, l'api ne démarrera plus.
Si vous êtes en cours de dev, vous pouvez peut-être vous permettre de supprimer la ligne du dernier passage de votre
script dans cette table.
Mais une fois mergé / livré, il est strictement interdit de revenir sur un script déjà joué.

Si un script est incorrect, il faudra alors faire un second script qui fait correctement le travail.

#### 5.1.4. Pour finir <a id="devutileflywayfinish" /> [&#9650;](#top)

* Regardez bien comment sont faits les autres fichiers SQL.
* Déclarez vous-mêmes vos contraintes pour que les noms soient les mêmes chez tous.
* Commentez les colonnes et les tables, et réutilisez ces commentaires comme javadoc sur vos entités
* Insérez des données dans la table dans un script de dev pour que les suivants puissent s'en servir directement.
