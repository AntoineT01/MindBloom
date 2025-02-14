-- V2__insert_initial_questions.sql

INSERT INTO question (
    quiz_id,
    content,
    type,
    points,
    question_order,
    is_required,
    is_active,
    display_time,
    time_min,
    time_max,
    created_at,
    updated_at,
    imported
) VALUES
-- Quiz 1 : Basic Mathematics Quiz
(1, 'What is 2 + 2?', 'multiple_choice', 10, 1, TRUE, TRUE, 30, 10, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(1, 'What is 5 * 3?', 'multiple_choice', 10, 2, TRUE, TRUE, 30, 10, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(1, 'Solve: 12 - 7', 'multiple_choice', 10, 3, TRUE, TRUE, 30, 10, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(1, 'What is the square root of 16?', 'multiple_choice', 10, 4, TRUE, TRUE, 30, 10, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(1, 'What is 7 + 6?', 'multiple_choice', 10, 5, TRUE, TRUE, 30, 10, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(1, 'Solve: 15 / 3', 'multiple_choice', 10, 6, TRUE, TRUE, 30, 10, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(1, 'What is 9 - 4?', 'multiple_choice', 10, 7, TRUE, TRUE, 30, 10, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 2 : World History Challenge
(2, 'Who was the first President of the United States?', 'multiple_choice', 15, 1, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(2, 'In which year did the French Revolution begin?', 'multiple_choice', 15, 2, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(2, 'Which empire was known as the "Sun Never Sets" empire?', 'multiple_choice', 15, 3, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(2, 'Who was the famous general of the Carthaginian forces?', 'multiple_choice', 15, 4, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(2, 'Which treaty ended World War I?', 'multiple_choice', 15, 5, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(2, 'Who discovered America?', 'multiple_choice', 15, 6, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(2, 'Which ancient civilization built the pyramids?', 'multiple_choice', 15, 7, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 3 : Science Trivia
(3, 'What is H2O commonly known as?', 'multiple_choice', 10, 1, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(3, 'Which planet is known as the Red Planet?', 'multiple_choice', 10, 2, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(3, 'What force keeps us on the ground?', 'multiple_choice', 10, 3, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(3, 'What is the chemical symbol for Gold?', 'multiple_choice', 10, 4, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(3, 'Which gas is most abundant in the Earth''s atmosphere?', 'multiple_choice', 10, 5, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(3, 'What phenomenon explains the bending of light?', 'multiple_choice', 10, 6, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(3, 'What is the basic unit of life?', 'multiple_choice', 10, 7, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 4 : Geography Bee
(4, 'What is the capital of France?', 'multiple_choice', 10, 1, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(4, 'Which river runs through London?', 'multiple_choice', 10, 2, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(4, 'Mount Everest is located in which mountain range?', 'multiple_choice', 10, 3, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(4, 'Which continent is the Sahara Desert located on?', 'multiple_choice', 10, 4, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(4, 'What is the largest country by area?', 'multiple_choice', 10, 5, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(4, 'What is the longest river in the world?', 'multiple_choice', 10, 6, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(4, 'Which ocean is the deepest?', 'multiple_choice', 10, 7, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 5 : Literature Knowledge Test
(5, 'Who wrote "Romeo and Juliet"?', 'multiple_choice', 15, 1, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(5, 'Which novel begins with "Call me Ishmael"?', 'multiple_choice', 15, 2, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(5, 'Who is the author of "1984"?', 'multiple_choice', 15, 3, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(5, 'Who wrote "Pride and Prejudice"?', 'multiple_choice', 15, 4, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(5, 'Which Shakespeare play features the character Hamlet?', 'multiple_choice', 15, 5, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(5, 'Who is the author of "The Great Gatsby"?', 'multiple_choice', 15, 6, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(5, 'Which poet wrote "The Raven"?', 'multiple_choice', 15, 7, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 6 : Computer Science Basics
(6, 'What does CPU stand for?', 'multiple_choice', 10, 1, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(6, 'Which programming language is known for its snake logo?', 'multiple_choice', 10, 2, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(6, 'What is the binary representation of the decimal number 10?', 'multiple_choice', 10, 3, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(6, 'What does RAM stand for?', 'multiple_choice', 10, 4, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(6, 'What is the time complexity of binary search?', 'multiple_choice', 10, 5, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(6, 'Which data structure uses FIFO principle?', 'multiple_choice', 10, 6, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(6, 'What is the function of an operating system?', 'multiple_choice', 10, 7, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 7 : Art Appreciation Quiz
(7, 'Which artist painted the Mona Lisa?', 'multiple_choice', 15, 1, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(7, 'In which century did Vincent van Gogh live?', 'multiple_choice', 15, 2, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(7, 'What art movement is Salvador Dalí associated with?', 'multiple_choice', 15, 3, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(7, 'Who painted "Starry Night"?', 'multiple_choice', 15, 4, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(7, 'Which museum houses the Mona Lisa?', 'multiple_choice', 15, 5, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(7, 'What style is Pablo Picasso known for?', 'multiple_choice', 15, 6, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(7, 'Which artist is famous for the sculpture "The Thinker"?', 'multiple_choice', 15, 7, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 8 : Music Theory Fundamentals
(8, 'What is the musical term for gradually getting louder?', 'multiple_choice', 10, 1, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(8, 'How many notes are there in a standard major scale?', 'multiple_choice', 10, 2, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(8, 'Which composer wrote "Für Elise"?', 'multiple_choice', 10, 3, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(8, 'What is the time signature of a waltz?', 'multiple_choice', 10, 4, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(8, 'What does "allegro" indicate in music?', 'multiple_choice', 10, 5, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(8, 'What is the term for the speed of a piece of music?', 'multiple_choice', 10, 6, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(8, 'Which instrument is central to a jazz band?', 'multiple_choice', 10, 7, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 9 : Economics Essentials
(9, 'What is the basic economic problem?', 'multiple_choice', 15, 1, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(9, 'Which term describes the amount of goods produced per unit time?', 'multiple_choice', 15, 2, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(9, 'What is inflation?', 'multiple_choice', 15, 3, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(9, 'What does GDP stand for?', 'multiple_choice', 15, 4, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(9, 'What is a market economy?', 'multiple_choice', 15, 5, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(9, 'What is the law of supply and demand?', 'multiple_choice', 15, 6, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(9, 'What is fiscal policy?', 'multiple_choice', 15, 7, TRUE, TRUE, 45, 20, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),

-- Quiz 10 : Language Proficiency Test
(10, 'What is a synonym for "vocabulary"?', 'multiple_choice', 10, 1, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(10, 'Which language is known as the language of Shakespeare?', 'multiple_choice', 10, 2, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(10, 'What is the past tense of "run"?', 'multiple_choice', 10, 3, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(10, 'What is an antonym for "happy"?', 'multiple_choice', 10, 4, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(10, 'What is the plural form of "child"?', 'multiple_choice', 10, 5, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(10, 'What is a homophone?', 'multiple_choice', 10, 6, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
(10, 'What is a common literary device used in poetry?', 'multiple_choice', 10, 7, TRUE, TRUE, 30, 10, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);
