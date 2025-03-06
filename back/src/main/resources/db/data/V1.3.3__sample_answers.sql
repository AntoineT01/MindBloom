-- V2__insert_initial_answers.sql

-- Quiz 1: Basic Mathematics Quiz (Questions 1-7)
-- Q1: What is 2 + 2?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (1, '3', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, '4', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, '2', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, '5', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q2: What is 5 * 3?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (2, '10', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, '15', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, '20', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, '25', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q3: Solve: 12 - 7
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (3, '4', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, '5', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, '6', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, '7', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q4: What is the square root of 16?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (4, '2', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, '4', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, '8', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, '6', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q5: What is 7 + 6?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (5, '12', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, '13', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, '14', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, '11', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q6: Solve: 15 / 3
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (6, '4', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, '5', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, '6', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, '7', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q7: What is 9 - 4?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (7, '4', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, '5', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, '6', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, '3', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 2: World History Challenge (Questions 8-14)
-- Q8: Who was the first President of the United States?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (8, 'Thomas Jefferson', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (8, 'George Washington', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (8, 'Abraham Lincoln', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (8, 'John Adams', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q9: In which year did the French Revolution begin?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (9, '1776', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (9, '1789', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (9, '1799', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (9, '1804', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q10: Which empire was known as the "Sun Never Sets" empire?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (10, 'Roman Empire', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10, 'British Empire', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10, 'Ottoman Empire', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10, 'Mongol Empire', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q11: Who was the famous general of the Carthaginian forces?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (11, 'Hannibal Barca', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (11, 'Julius Caesar', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (11, 'Alexander the Great', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (11, 'Scipio Africanus', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q12: Which treaty ended World War I?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (12, 'Treaty of Paris', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (12, 'Treaty of Versailles', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (12, 'Treaty of Tordesillas', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (12, 'Treaty of Ghent', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q13: Who discovered America?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (13, 'Christopher Columbus', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (13, 'Vasco da Gama', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (13, 'Ferdinand Magellan', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (13, 'Leif Erikson', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q14: Which ancient civilization built the pyramids?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (14, 'Egyptians', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (14, 'Romans', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (14, 'Greeks', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (14, 'Mayans', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 3: Science Trivia (Questions 15-21)
-- Q15: What is H2O commonly known as?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (15, 'Oxygen', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (15, 'Water', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (15, 'Hydrogen', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (15, 'Saltwater', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q16: Which planet is known as the Red Planet?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (16, 'Jupiter', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (16, 'Mars', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (16, 'Saturn', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (16, 'Venus', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q17: What force keeps us on the ground?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (17, 'Magnetism', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (17, 'Gravity', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (17, 'Friction', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (17, 'Electromagnetism', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q18: What is the chemical symbol for Gold?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (18, 'Ag', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (18, 'Au', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (18, 'Gd', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (18, 'Go', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q19: Which gas is most abundant in the Earth's atmosphere?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (19, 'Oxygen', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (19, 'Nitrogen', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (19, 'Carbon Dioxide', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (19, 'Argon', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q20: What phenomenon explains the bending of light?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (20, 'Reflection', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (20, 'Refraction', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (20, 'Diffraction', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (20, 'Dispersion', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q21: What is the basic unit of life?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (21, 'Atom', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (21, 'Cell', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (21, 'Molecule', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (21, 'Organism', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 4: Geography Bee (Questions 22-28)
-- Q22: What is the capital of France?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (22, 'Lyon', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (22, 'Paris', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (22, 'Marseille', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (22, 'Nice', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q23: Which river runs through London?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (23, 'Seine', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (23, 'Thames', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (23, 'Danube', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (23, 'Rhine', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q24: Mount Everest is located in which mountain range?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (24, 'Andes', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (24, 'Himalayas', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (24, 'Rockies', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (24, 'Alps', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q25: Which continent is the Sahara Desert located on?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (25, 'Asia', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (25, 'Africa', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (25, 'Australia', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (25, 'South America', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q26: What is the largest country by area?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (26, 'Canada', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (26, 'Russia', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (26, 'USA', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (26, 'China', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q27: What is the longest river in the world?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (27, 'Amazon', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (27, 'Nile', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (27, 'Yangtze', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (27, 'Mississippi', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q28: Which ocean is the deepest?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (28, 'Atlantic Ocean', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (28, 'Pacific Ocean', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (28, 'Indian Ocean', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (28, 'Arctic Ocean', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 5: Literature Knowledge Test (Questions 29-35)
-- Q29: Who wrote 'Romeo and Juliet'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (29, 'Charles Dickens', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (29, 'William Shakespeare', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (29, 'Jane Austen', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (29, 'Mark Twain', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q30: Which novel begins with 'Call me Ishmael'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (30, 'The Old Man and the Sea', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (30, 'Moby-Dick', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (30, 'Heart of Darkness', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (30, 'Treasure Island', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q31: Who is the author of '1984'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (31, 'George Orwell', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (31, 'Aldous Huxley', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (31, 'Ray Bradbury', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (31, 'J.D. Salinger', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q32: Who wrote 'Pride and Prejudice'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (32, 'Jane Austen', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (32, 'Charlotte Brontë', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (32, 'Emily Brontë', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (32, 'Mary Shelley', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q33: Which Shakespeare play features the character Hamlet?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (33, 'Hamlet', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (33, 'Macbeth', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (33, 'Othello', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (33, 'King Lear', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q34: Who is the author of 'The Great Gatsby'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (34, 'F. Scott Fitzgerald', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (34, 'Ernest Hemingway', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (34, 'John Steinbeck', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (34, 'William Faulkner', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q35: Which poet wrote 'The Raven'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (35, 'Edgar Allan Poe', 'multiple_choice', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (35, 'Walt Whitman', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (35, 'Robert Frost', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (35, 'Emily Dickinson', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q36: What does CPU stand for?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (36, 'Computer Processing Unit', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (36, 'Central Processing Unit', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (36, 'Central Performance Unit', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (36, 'Control Processing Unit', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q37: Which programming language is known for its snake logo?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (37, 'Java', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (37, 'Python', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (37, 'C++', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (37, 'Ruby', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q38: What is the binary representation of the decimal number 10?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (38, '1001', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (38, '1010', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (38, '1100', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (38, '1110', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q39: What does RAM stand for?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (39, 'Readily Available Memory', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (39, 'Random Access Memory', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (39, 'Rapid Application Module', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (39, 'Read Access Memory', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q40: What is the time complexity of binary search?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (40, 'O(n)', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (40, 'O(log n)', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (40, 'O(n log n)', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (40, 'O(1)', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q41: Which data structure uses FIFO principle?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (41, 'Stack', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (41, 'Queue', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (41, 'Tree', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (41, 'Graph', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q42: What is the function of an operating system?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (42, 'Process user input only', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (42, 'Only manage files', 'multiple_choice', FALSE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (42, 'Only run applications', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (42, 'Manage hardware and software resources', 'multiple_choice', TRUE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 7: Art Appreciation Quiz (Questions 43-49)
-- Q43: Which artist painted the Mona Lisa?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (43, 'Pablo Picasso', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (43, 'Leonardo da Vinci', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (43, 'Vincent van Gogh', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (43, 'Michelangelo', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q44: In which century did Vincent van Gogh live?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (44, '18th century', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (44, '19th century', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (44, '20th century', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (44, '17th century', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q45: What art movement is Salvador Dalí associated with?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (45, 'Impressionism', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (45, 'Surrealism', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (45, 'Cubism', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (45, 'Baroque', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q46: Who painted 'Starry Night'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (46, 'Claude Monet', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (46, 'Vincent van Gogh', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (46, 'Paul Cézanne', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (46, 'Edvard Munch', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q47: Which museum houses the Mona Lisa?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (47, 'The British Museum', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (47, 'The Louvre', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (47, 'The Met', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (47, 'The Prado', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q48: What style is Pablo Picasso known for?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (48, 'Realism', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (48, 'Cubism', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (48, 'Impressionism', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (48, 'Fauvism', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q49: Which artist is famous for the sculpture 'The Thinker'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (49, 'Donatello', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (49, 'Auguste Rodin', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (49, 'Gian Lorenzo Bernini', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (49, 'Henry Moore', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 8: Music Theory Fundamentals (Questions 50-56)
-- Q50: What is the musical term for gradually getting louder?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (50, 'Decrescendo', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (50, 'Crescendo', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (50, 'Staccato', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (50, 'Legato', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q51: How many notes are there in a standard major scale?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (51, '5', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (51, '7', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (51, '8', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (51, '12', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q52: Which composer wrote 'Für Elise'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (52, 'Wolfgang Amadeus Mozart', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (52, 'Ludwig van Beethoven', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (52, 'Johann Sebastian Bach', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (52, 'Franz Schubert', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q53: What is the time signature of a waltz?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (53, '4/4', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (53, '3/4', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (53, '2/4', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (53, '6/8', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q54: What does 'allegro' indicate in music?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (54, 'Slow tempo', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (54, 'Fast tempo', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (54, 'Loud volume', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (54, 'Soft dynamics', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q55: What is the term for the speed of a piece of music?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (55, 'Rhythm', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (55, 'Tempo', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (55, 'Meter', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (55, 'Pitch', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q56: Which instrument is central to a jazz band?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (56, 'Violin', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (56, 'Saxophone', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (56, 'Piano', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (56, 'Flute', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 9: Economics Essentials (Questions 57-63)
-- Q57: What is the basic economic problem?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (57, 'Abundance', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (57, 'Scarcity', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (57, 'Inflation', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (57, 'Deflation', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q58: Which term describes the amount of goods produced per unit time?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (58, 'Efficiency', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (58, 'Productivity', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (58, 'Output', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (58, 'Capacity', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q59: What is inflation?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (59, 'A decrease in supply', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (59, 'A general increase in prices', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (59, 'An increase in demand', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (59, 'A market bubble', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q60: What does GDP stand for?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (60, 'Global Domestic Product', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (60, 'Gross Domestic Product', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (60, 'Gross Development Product', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (60, 'General Domestic Product', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q61: What is a market economy?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (61, 'An economy controlled by the government', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       (61, 'An economy based on supply and demand', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (61, 'A barter system', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (61, 'A command economy', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q62: What is the law of supply and demand?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (62, 'Price is fixed by the government', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (62, 'Price is determined by supply and demand', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       (62, 'Demand always exceeds supply', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (62, 'Supply always exceeds demand', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q63: What is fiscal policy?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (63, 'Monetary policy', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (63, 'Government spending and taxation policy', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       (63, 'Trade policy', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (63, 'Foreign policy', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Quiz 10: Language Proficiency Test (Questions 64-70)
-- Q64: What is a synonym for 'vocabulary'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (64, 'Grammar', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (64, 'Lexicon', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (64, 'Syntax', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (64, 'Diction', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q65: Which language is known as the language of Shakespeare?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (65, 'French', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (65, 'English', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (65, 'German', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (65, 'Italian', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q66: What is the past tense of 'run'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (66, 'Runned', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (66, 'Ran', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (66, 'Running', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (66, 'Runs', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q67: What is an antonym for 'happy'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (67, 'Joyful', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (67, 'Sad', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (67, 'Elated', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (67, 'Cheerful', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q68: What is the plural form of 'child'?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (68, 'Childs', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (68, 'Children', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (68, 'Childer', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (68, 'Childrens', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q69: What is a homophone?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (69, 'A word with multiple meanings', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (69, 'A word that sounds the same as another', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (69, 'A word that is spelled the same', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (69, 'A word that is pronounced differently', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Q70: What is a common literary device used in poetry?
INSERT INTO answer (question_id, content, type, is_correct, answer_order, created_at, updated_at)
VALUES (70, 'Alliteration', 'multiple_choice', FALSE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (70, 'Metaphor', 'multiple_choice', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (70, 'Onomatopoeia', 'multiple_choice', FALSE, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (70, 'Hyperbole', 'multiple_choice', FALSE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
