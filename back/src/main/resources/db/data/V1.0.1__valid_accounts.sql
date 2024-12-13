insert into account (id, profile_id, firstname, lastname, mail, password, oauth_provider, oauth_id, locale, active)
values (1, 1, 'The', 'Admin', 'admin@example.com', '$2a$10$QAc7Aiew2DDpCJB32OWB2Omy/iwN/YMVKUdawYYbqBZ0Wjo3TTfIy', null,
        null, 'fr', true),
       (2, 2, 'Normal', 'User', 'user@example.com', '$2a$10$6YE.uYWHNTo1qf6/SOUypOL1vfnrhBBzhpksP6V.z2gMpMcFb/FbG',
        null, null, 'fr', true),
       (3, 2, null, null, 'googleuser@example.com', null, 'google', 'GOOGLE_OAUTH_ID', 'fr', true),
       (4, 2, null, null, 'microsoftuser@example.com', null, 'microsoft', 'MICROSOFT_OAUTH_ID', 'en', true),
       (5, 2, 'Facebook', 'User', 'fbuser@example.com', null, 'facebook', 'FACEBOOK_OAUTH_ID', 'en', true);
