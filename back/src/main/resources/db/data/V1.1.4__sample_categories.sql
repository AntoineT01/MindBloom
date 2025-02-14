-- V1.1.4__sample_categories.sql
-- Exemple d'insertion de quelques catégories pour tester la table "categorie"

-- La table "categorie" a pour colonnes : id (auto-incr), name (varchar), description (text)
-- Vous n'avez pas mentionné de timestamps ici, donc on se limite à name/description.

INSERT INTO categorie (name, description)
VALUES
    ('Basic Mathematics', 'Catégorie axée sur l’arithmétique et les opérations de base.'),
    ('World History', 'Catégorie couvrant des événements et personnages historiques.'),
    ('Science Trivia', 'Catégorie couvrant différents domaines scientifiques, de la physique à la biologie.'),
    ('Geography Bee', 'Catégorie explorant les pays, capitales et la géographie mondiale.'),
    ('Literature', 'Catégorie portant sur la littérature, les auteurs, les œuvres classiques et contemporaines.'),
    ('Computer Science', 'Catégorie pour les bases de l’informatique, de la programmation et du hardware.'),
    ('Art Appreciation', 'Catégorie portant sur les grands mouvements artistiques et les artistes célèbres.'),
    ('Music Theory', 'Catégorie pour la théorie musicale, les compositeurs, les instruments.'),
    ('Economics Essentials', 'Catégorie abordant les concepts économiques tels que l’offre, la demande et la monnaie.'),
    ('Language Proficiency', 'Catégorie axée sur la grammaire, le vocabulaire et les subtilités linguistiques.');
