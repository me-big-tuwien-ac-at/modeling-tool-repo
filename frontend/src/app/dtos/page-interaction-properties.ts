import {Technology} from './technology';
import {Category} from './category';
import {License} from './license';

export interface Filter {
  displayed: boolean;
  columns: ColumnsDisplayed;
  tools: ToolProperties;
}

/**
 * Image properties on the main page.
 */
export interface ImgZoomIn {
  zoomedIn: ZoomStatus;
  src: string;
  alt: string;
  directionExpansion: DirectionExpansion;
}

export enum DirectionExpansion {
  height,
  width
}

/**
 * Collapsible sections on the main page.
 */
export interface Section {
  generalExpanded: boolean;
  examplesExpanded: boolean;
  detailsExpanded: boolean;
}

export enum ZoomStatus {
  hidden,
  initialized,
  shown
}

interface ColumnsDisplayed {
  displayed: boolean;
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

interface ToolProperties {
  displayed: boolean;
  name?: string;
  link?: string;
  openSource?: boolean;
  technology?: Technology[];
  webApp?: boolean;
  desktopApp?: boolean;
  category?: Category;
  license?: License;
  loginRequired?: boolean;
  realTimeCollab?: boolean;
  creator?: string;
  platform?: string[];
  programmingLanguage?: string[];
}
