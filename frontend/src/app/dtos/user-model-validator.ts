/**
 * Class containing error messages for corresponding properties if the user picks or types invalid data.
 */
export interface UserModelValidator {
  name: {
    syntactic?: String
    duplicate?: {
      id: number
      name: string
    }
  };
  toolNotEdited?: string;
  link?: string;
  email?: string;
  modelingLanguage?: string;
  platform?: string;
  programmingLanguage?: string;
}
