import {Technology} from './technology';
import {License} from './license';
import {Category} from './category';

export interface ModelingTool {
  id?: number;
  modelingToolId?: number;
  name: string;
  link: string;
  openSource?: boolean;
  technology: Technology[];
  webApp?: boolean;
  desktopApp?: boolean;
  category?: Category;
  modelingLanguages: string[];
  sourceCodeGeneration?: boolean;
  cloudService?: boolean;
  license?: License;
  loginRequired?: boolean;
  realTimeCollab?: boolean;
  creator?: string[];
  platform: string[];
  programmingLanguage: string[];
  feedback?: string;
  userEmail?: string;
}

export interface ModelingToolSearch {
  name?: string;
  link?: string;
  openSource?: boolean;
  technology?: Technology[];
  webApp?: boolean;
  desktopApp?: boolean;
  category?: Category;
  modelingLanguages?: string[];
  license?: License;
  loginRequired?: boolean;
  realTimeCollab?: boolean;
  creator?: string;
  platform?: string[];
  programmingLanguage?: string[];
}

export interface CheckedModelingToolColumns {
  openSource: boolean;
  appFramework: boolean;
  webApp: boolean;
  desktopApp: boolean;
  category: boolean;
  modelingLanguages: boolean;
  sourceCodeGeneration: boolean;
  cloudService: boolean;
  license: boolean;
  loginRequired: boolean;
  realTimeCollab: boolean;
  creator: boolean;
  os: boolean;
  programmingLanguage: boolean;
}
