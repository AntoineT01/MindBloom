interface LoginData {
    email: string;
    password: string;
}

interface SignupData {
    firstname: string;
    lastname: string;
    mail: string;
    password: string;
}

interface LoginResponse {
    token?: string;
    user?: any;
    message?: string;
}

interface SignupResponse {
    message?: string;
    success?: boolean;
}

export async function loginUser(data: LoginData): Promise<LoginResponse> {
    try {
        console.log('Tentative de connexion avec:', data);

        const url = '/api/login';
        console.log('URL de l\'API:', url);

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: data.email,
                password: data.password
            }),
        });
        console.log('Response status:', response.status);

        // Si la connexion est réussie, requête HTTP 200 sans corps
        if (response.status === 200) {
            console.log('Connexion réussie');

            // Récupération des informations de l'utilisateur
            const userData = await fetchUserData();

            // Création d'un token fictif pour stocker l'état de connexion
            // Puisque l'API ne renvoie pas de token
            if (typeof window !== 'undefined') {
                const sessionToken = 'connected_' + new Date().getTime();
                localStorage.setItem('auth_token', sessionToken);
                if (userData) {
                    localStorage.setItem('user_data', JSON.stringify(userData));
                }
            }

            return {
                token: 'connected',
                user: userData,
                message: 'Connexion réussie'
            };
        }

        // Gestion des erreurs
        if (response.status === 400) {
            throw new Error('Données invalides');
        } else if (response.status === 401) {
            throw new Error('Email ou mot de passe incorrect');
        } else if (response.status === 409) {
            throw new Error('Conflit avec les données existantes');
        } else if (response.status === 500) {
            throw new Error('Erreur serveur');
        } else {
            throw new Error('Une erreur est survenue');
        }
    } catch (error) {
        console.error('Login error in service:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de la connexion');
    }
}

export async function registerUser(data: SignupData): Promise<SignupResponse> {
    try {
        console.log('Tentative d\'inscription avec:', data);

        const url = '/api/signup';
        console.log('URL de l\'API:', url);

        // Format exactement selon le Swagger
        const signupData = {
            firstname: data.firstname,
            lastname: data.lastname,
            mail: data.mail,         // C'est bien 'mail' et non 'email'
            password: data.password,
            locale: "fr"             // Valeur par défaut requise selon le Swagger
            // oauthProvider et oauthId sont optionnels
        };

        console.log('Données envoyées à l\'API:', JSON.stringify(signupData, null, 2));

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(signupData),
        });
        console.log('Response status:', response.status);

        if (!response.ok) {
            // Essayer de lire les détails de l'erreur
            try {
                const errorData = await response.json();
                console.error('Détails de l\'erreur:', errorData);

                // Afficher tous les messages d'erreur spécifiques
                if (errorData.errors && errorData.errors.length > 0) {
                    console.error('Messages d\'erreur spécifiques:');
                    errorData.errors.forEach((err: any, index: number) => {
                        console.error(`Erreur ${index + 1}:`, err.code, '-', err.message);
                    });

                    // Construire un message d'erreur avec tous les détails
                    const errorMessages = errorData.errors.map((err: any) => err.message).join('; ');
                    throw new Error(errorMessages || `Erreur ${response.status}: ${response.statusText}`);
                }
            } catch (e) {
                // Si on ne peut pas lire le JSON ou s'il n'y a pas de détails d'erreur
                console.error('Impossible de parser les détails de l\'erreur:', e);
                throw new Error(`Erreur ${response.status}: ${response.statusText}`);
            }
            throw new Error(`Erreur ${response.status}: ${response.statusText}`);
        }

        try {
            // Si la réponse est OK, on essaie de lire les données de réponse
            const responseData = await response.json();
            console.log('Inscription réussie:', responseData);

            return {
                success: true,
                message: 'Inscription réussie. Veuillez vérifier votre email pour confirmer votre compte.'
            };
        } catch (e) {
            // Si la réponse ne contient pas de JSON valide
            console.log('La réponse ne contient pas de JSON valide');
            return {
                success: true,
                message: 'Inscription réussie. Veuillez vérifier votre email pour confirmer votre compte.'
            };
        }
    } catch (error) {
        console.error('Registration error in service:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de l\'inscription');
    }
}

// Fonction pour récupérer les données de l'utilisateur connecté
async function fetchUserData(): Promise<any> {
    try {
        const response = await fetch('/api/accounts/me', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            console.error('Erreur lors de la récupération des données utilisateur:', response.status);
            return null;
        }

        const userData = await response.json();
        console.log('Données utilisateur récupérées:', userData);

        return {
            id: userData.id,
            firstname: userData.firstname,
            lastname: userData.lastname,
            email: userData.mail,
            profile: userData.profile,
            // Génération d'un avatar basé sur le nom si aucun n'est fourni
            avatar: `https://api.dicebear.com/7.x/initials/svg?seed=${userData.firstname}+${userData.lastname}`
        };
    } catch (error) {
        console.error('Erreur lors de la récupération des données utilisateur:', error);
        return null;
    }
}

// Fonction pour vérifier si l'utilisateur est connecté
export function isUserLoggedIn(): boolean {
    if (typeof window === 'undefined') return false;
    const token = localStorage.getItem('auth_token');
    // Vérification simplifiée - vérifier si un token est stocké
    return !!token;
}

// Fonction pour récupérer le token d'authentification
export function getAuthToken(): string | null {
    if (typeof window === 'undefined') return null;
    return localStorage.getItem('auth_token');
}

export function getUserData(): any | null {
    if (typeof window === 'undefined') return null;

    const userData = localStorage.getItem('user_data');
    if (userData) {
        try {
            return JSON.parse(userData);
        } catch (error) {
            console.error('Erreur lors du parsing des données utilisateur:', error);
            return null;
        }
    }
    return null;
}

export function logoutUser(): void {
    if (typeof window !== 'undefined') {
        localStorage.removeItem('auth_token');
        localStorage.removeItem('user_data');
        console.log('Déconnexion réussie, tokens supprimés');
    }
}