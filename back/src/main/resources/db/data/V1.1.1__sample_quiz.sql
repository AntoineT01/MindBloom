-- V2__insert_initial_quizzes.sql

INSERT INTO quiz (
    title,
    description,
    creator_id,
    is_public,
    show_answers,
    show_final_score,
    time_limit,
    status,
    share_code
) VALUES
      (
          'Basic Mathematics Quiz',
          'Test your fundamental math skills with this quiz covering addition, subtraction, multiplication, and division.',
          1,
          TRUE,
          TRUE,
          TRUE,
          60,
          'ACTIVE',
          'MATH_BASIC_001'
      ),
      (
          'World History Challenge',
          'Explore significant events and figures from ancient to modern times in this comprehensive history quiz.',
          2,
          TRUE,
          FALSE,
          TRUE,
          120,
          'ACTIVE',
          'HISTORY_WORLD_002'
      ),
      (
          'Science Trivia',
          'A fun quiz to assess your knowledge in various scientific fields including physics, chemistry, and biology.',
          1,
          FALSE,
          TRUE,
          FALSE,
          90,
          'ACTIVE',
          'SCIENCE_TRIVIA_003'
      ),
      (
          'Geography Bee',
          'Identify countries, capitals, and landmarks in this engaging geography quiz.',
          3,
          TRUE,
          FALSE,
          TRUE,
          75,
          'INACTIVE',
          'GEOGRAPHY_BEE_004'
      ),
      (
          'Literature Knowledge Test',
          'Assess your understanding of classic and modern literary works and authors.',
          2,
          FALSE,
          TRUE,
          TRUE,
          100,
          'ACTIVE',
          'LITERATURE_TEST_005'
      ),
      (
          'Computer Science Basics',
          'Evaluate your grasp of fundamental computer science concepts and programming principles.',
          3,
          TRUE,
          TRUE,
          TRUE,
          110,
          'ACTIVE',
          'CS_BASICS_006'
      ),
      (
          'Art Appreciation Quiz',
          'Discover your knowledge about various art movements, famous artists, and iconic artworks.',
          1,
          FALSE,
          FALSE,
          TRUE,
          80,
          'DELETED',
          'ART_APPRECIATION_007'
      ),
      (
          'Music Theory Fundamentals',
          'Test your understanding of music theory, including scales, chords, and rhythms.',
          2,
          TRUE,
          TRUE,
          FALSE,
          95,
          'ACTIVE',
          'MUSIC_THEORY_008'
      ),
      (
          'Economics Essentials',
          'A quiz designed to evaluate your knowledge of basic economic principles and theories.',
          3,
          FALSE,
          TRUE,
          TRUE,
          85,
          'INACTIVE',
          'ECONOMICS_ESSENTIALS_009'
      ),
      (
          'Language Proficiency Test',
          'Assess your proficiency in English grammar, vocabulary, and comprehension.',
          1,
          TRUE,
          FALSE,
          TRUE,
          70,
          'ACTIVE',
          'LANGUAGE_PROF_010'
      );
