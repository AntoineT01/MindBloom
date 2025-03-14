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
    user?: any;
    message?: string;
    success?: boolean;
}

interface SignupResponse {
    message?: string;
    success?: boolean;
}

interface UpdateProfileData {
    id: number;
    firstname: string;
    lastname: string;
    mail: string;
    profile?: {
        id: number;
        label: string;
    };
    locale?: string;
    oauthProvider?: string;
    oauthId?: string;
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
            credentials: 'include',  // Important pour recevoir et envoyer les cookies
            body: JSON.stringify({
                email: data.email,
                password: data.password
            }),
        });
        console.log('Response status:', response.status);

        // Si la connexion est réussie
        if (response.status === 200) {
            console.log('Connexion réussie - Cookie JWT reçu');

            // Marquer l'utilisateur comme connecté dans le localStorage
            if (typeof window !== 'undefined') {
                localStorage.setItem('is_authenticated', 'true');
            }

            // Récupération des informations de l'utilisateur
            const userData = await fetchUserData();

            if (userData && typeof window !== 'undefined') {
                localStorage.setItem('user_data', JSON.stringify(userData));
            }

            return {
                user: userData,
                message: 'Connexion réussie',
                success: true
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
            mail: data.mail,
            password: data.password,
            locale: "fr"
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
                'Content-Type': 'application/json'
            },
            credentials: 'include'  // Important pour envoyer les cookies d'authentification
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
            locale: userData.locale,
            // Génération d'un avatar basé sur le nom si aucun n'est fourni
            avatar: `https://api.dicebear.com/7.x/initials/svg?seed=${userData.firstname}+${userData.lastname}`
        };
    } catch (error) {
        console.error('Erreur lors de la récupération des données utilisateur:', error);
        return null;
    }
}

export async function updateUserProfile(profileData: UpdateProfileData): Promise<any> {
    try {
        console.log('Tentative de mise à jour du profil:', profileData);

        // Utilisation de la bonne route API selon la documentation Swagger
        const url = `/api/accounts/me`;
        console.log('URL de l\'API:', url);

        // Vérifier si l'utilisateur est authentifié
        if (!isUserLoggedIn()) {
            throw new Error('Utilisateur non authentifié');
        }

        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',  // Important pour envoyer les cookies d'authentification
            body: JSON.stringify(profileData),
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

        // Si la mise à jour est réussie
        const updatedUserData = await response.json();
        console.log('Mise à jour du profil réussie:', updatedUserData);

        // Mettre à jour les données utilisateur dans le localStorage
        if (typeof window !== 'undefined') {
            localStorage.setItem('user_data', JSON.stringify({
                id: updatedUserData.id,
                firstname: updatedUserData.firstname,
                lastname: updatedUserData.lastname,
                email: updatedUserData.mail,
                profile: updatedUserData.profile,
                locale: updatedUserData.locale,
                avatar: `https://api.dicebear.com/7.x/initials/svg?seed=${updatedUserData.firstname}+${updatedUserData.lastname}`
            }));
        }

        return updatedUserData;
    } catch (error) {
        console.error('Error updating profile:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de la mise à jour du profil');
    }
}

// Fonction pour vérifier si l'utilisateur est connecté
export function isUserLoggedIn(): boolean {
    if (typeof window === 'undefined') return false;
    return localStorage.getItem('is_authenticated') === 'true';
}

// Fonction pour récupérer les données utilisateur stockées localement
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

// Fonction de déconnexion
export function logoutUser(): void {
    if (typeof window !== 'undefined') {
        // Supprimer les données locales
        localStorage.removeItem('is_authenticated');
        localStorage.removeItem('user_data');

        // Appeler l'API de déconnexion pour supprimer le cookie côté serveur
        fetch('/api/logout', {
            method: 'POST',
            credentials: 'include'  // Important pour envoyer les cookies d'authentification
        }).then(() => {
            console.log('Déconnexion réussie côté serveur');
        }).catch(error => {
            console.error('Erreur lors de la déconnexion côté serveur:', error);
        });

        console.log('Déconnexion réussie, données supprimées');
    }
}