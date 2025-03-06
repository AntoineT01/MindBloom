import CryptoJS from 'crypto-js';

const generateSalt = (): string => {
  return CryptoJS.lib.WordArray.random(128 / 8).toString(); // Génère un sel de 16 octets
};

export const encryptPassword = (password: string, salt: string): string => {
  const secretKey = 'your-secret-keyy'; // Remplace par ta clé secrète réelle
  const saltedPassword = password + salt;

  // Génère un vecteur d'initialisation (IV)
  const iv = CryptoJS.lib.WordArray.random(128 / 8);

  // Chiffre en mode CBC avec le padding PKCS7 (équivalent à PKCS5)
  const encrypted = CryptoJS.AES.encrypt(saltedPassword, CryptoJS.enc.Utf8.parse(secretKey), { iv: iv });

  // Retourne l'IV concaténé avec le texte chiffré (sous forme de chaîne Base64)
  return iv.toString(CryptoJS.enc.Hex) + ':' + encrypted.toString();
};

export const generateSaltAndEncryptPassword = (password: string): string => {
  const salt = generateSalt();
  return encryptPassword(password, salt);
};
