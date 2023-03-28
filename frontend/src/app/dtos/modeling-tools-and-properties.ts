import {ModelingTool} from './modeling-tools';

export interface ModelingToolsAndProperties {
  modelingTools: ModelingTool[];
  modelingLanguages: string[];
  platforms: string[];
  programmingLanguages: string[];
}

export interface ModelingToolAndPropertiesAndEdit {
  modelingTools: ModelingTool[];
  modelingTool: ModelingTool;
  modelingLanguages: string[];
  platforms: string[];
  programmingLanguages: string[];
}
