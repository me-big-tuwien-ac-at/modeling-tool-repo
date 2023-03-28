import {Component, HostListener, OnInit} from '@angular/core';
import {ModelingToolsService} from '../services/modeling-tools.service';
import {CheckedModelingToolColumns, ModelingTool, ModelingToolSearch} from '../dtos/modeling-tools';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {getAllTechnologiesAsString, getTechnology, getTechnologyAsString, Technology} from '../dtos/technology';
import {getCategoryAsString} from '../dtos/category';
import {getLicenseAsString} from '../dtos/license';
import {getPlatformAsString} from '../dtos/platform'
import {ModelingToolsAndProperties} from '../dtos/modeling-tools-and-properties';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {SharedService} from '../services/shared.service';
import {ThemeEnum} from '../dtos/theme';
import {DirectionExpansion, ImgZoomIn, Section, ZoomStatus} from '../dtos/page-interaction-properties';

@Component({
  selector: 'app-modeling-tools',
  templateUrl: './modeling-tools.component.html',
  styleUrls: ['./modeling-tools.component.scss']
})
export class ModelingToolsComponent implements OnInit {
  modelingTools: ModelingTool[] = [];
  modelingToolSearch: ModelingToolSearch = {};
  modelingToolsFiltered: ModelingTool[] = [];
  checkedColumns: CheckedModelingToolColumns = {
    openSource: true,
    appFramework: true,
    webApp: true,
    desktopApp: true,
    category: true,
    modelingLanguages: true,
    sourceCodeGeneration: true,
    cloudService: true,
    license: true,
    loginRequired: true,
    realTimeCollab: false,
    creator: false,
    os: false,
    programmingLanguage: false
  };

  // Checks for pop up windows
  public technologyChecked = false;
  public searchTechnologyChecked = false;
  public searchPlatformChecked = false;
  public searchProgrammingLanguageChecked = false;
  public searchModelingLanguagesChecked = false;
  public platformsChecked = false;
  public programmingLanguagesChecked = false;
  public creatorInput = '';

  public modelingLanguages: string[] = [];
  public suggestedModelingLanguage = '';
  public platforms: string[] = [];
  public suggestedPlatforms = '';
  public programmingLanguages: string[] = [];
  public suggestedProgrammingLanguage = '';

  // Captcha properties
  public siteKey: string = '6Ldw5bkkAAAAACArt28AdO4SWq6YtSbRlwaeHaTN';
  public resolvedByUser: string = '';

  // Information collapsible
  public sections: Section = {
    generalExpanded: true,
    examplesExpanded: false,
    detailsExpanded: false
  }

  // Downloadable files
  public downloadJson: SafeUrl = '';

  // TODO: Replace with pageInteractionProperties - Filter interface
  public filterDisplayed = false;
  public filterColumnsDisplayed = true;
  public filterToolsDisplayed = true;

  // Sorting key
  private sortingKey: string = '';
  private order: number = 1;

  public theme: ThemeEnum = ThemeEnum.light;
  public phoneMode: boolean = false;

  public imgZoomedIn: ImgZoomIn = {
    zoomedIn: ZoomStatus.hidden,
    src: '',
    alt: '',
    directionExpansion: DirectionExpansion.width
  }
  public displayConfirmation: ZoomStatus = ZoomStatus.hidden;

  constructor(
    private modalService: NgbModal,
    private sanitizer: DomSanitizer,
    private service: ModelingToolsService,
    private sharedService: SharedService
  ) {
    this.sharedService.getTheme().subscribe({
      next: (data: ThemeEnum) => {
        this.theme = data;
      }
    });
    this.sharedService.setTheme();
  }

  @HostListener('window:resize', ['$event'])
  // @ts-ignore
  onResize(event): void {
    this.phoneMode = event.target.innerWidth < 1150;
  }

  ngOnInit(): void {
    this.sharedService.getConfirmation().subscribe({
      next: (data: ZoomStatus) => {
        if (data) {
          this.displayConfirmation = ZoomStatus.initialized;
        }
      }
    });
    this.sharedService.aClickedEvent.subscribe((data: ThemeEnum) => console.log(data));
    this.loadModelingToolsAndProperties();
    this.optimizeColumns();
  }

  /**
   * Loads all verified modeling tools, modeling languages, platforms and programming languages from the backend.
   *
   * @private
   */
  private loadModelingToolsAndProperties() {
    this.service.getModelingToolsAndProperties().subscribe({
      next: (data: ModelingToolsAndProperties) => {
        this.modelingTools = data.modelingTools;
        this.modelingToolsFiltered = data.modelingTools;
        this.modelingLanguages = data.modelingLanguages;
        this.platforms = data.platforms;
        this.programmingLanguages = data.programmingLanguages;
        this.sharedService.setTheme();
      },
      error: err => {
        console.log('Error fetching modeling tools and properties', err);
        if (err.status === 0) {
          console.log('Is the backend up?');
        }
      }
    })
  }

  /**
   * Downloads all modeling tools as JSON.
   */
  public downloadModelingToolsAsJson(): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(
      "data:text/json;charset=UTF-8," + encodeURIComponent(JSON.stringify(this.modelingTools))
    );
  }

  public downloadModelingToolsAsCsv(): SafeUrl {
    let csv: string = 'name;link;openSource;technology;webApp;desktopApp;category;modelingLanguages;' +
      'sourceCodeGeneration;cloudService;license;loginRequired;realTimeCollab;creator;platform;programmingLanguages\n';
    this.modelingTools.forEach((tool: ModelingTool) => {
      csv += `${tool.name};${tool.link};${tool.openSource};${this.arrayToList(tool.technology)};${tool.webApp};${tool.desktopApp};${tool.category};${this.arrayToList(tool.modelingLanguages)};${tool.sourceCodeGeneration};${tool.cloudService};${tool.license};${tool.loginRequired};${tool.realTimeCollab};${this.arrayToList(tool.creator)};${this.arrayToList(tool.platform)};${this.arrayToList(tool.programmingLanguage)};\n`;
    });
    return this.sanitizer.bypassSecurityTrustUrl(
      "data:text/csv;charset=UTF-8," + encodeURIComponent(csv)
    );
  }

  private arrayToList(array: Object[] | undefined): string {
    if (array === undefined || array === null || array.length === 0) {
      return '';
    }
    let result = '';
    array.forEach(entry => {
      result += `${entry},`;
    });
    return result.slice(0, -1);
  }

  /**
   * Sets {@code resolvedByUser} to true when the user confirms he is not a user.
   *
   * @param captchaResponse response by the captcha tag
   */
  public resolved(captchaResponse: string): void {
    this.resolvedByUser = captchaResponse;
  }

  /**
   * Filters Modeling Tools that fit the filtering options
   */
  public searchModelingTool(): void {
    this.service.filterModelingTools(this.modelingToolSearch).subscribe({
      next: (tools: ModelingTool[]) => {
        this.modelingToolsFiltered = tools;
      },
      error: err => {
        if (err.status === 0) {
          console.log('Is the backend up?', err);
        } else {
          console.log(err);
        }
      }

    });
  }

  public closeConfirmationWindow(): void {
    if (this.displayConfirmation === ZoomStatus.initialized) {
      this.displayConfirmation = ZoomStatus.shown;
    } else if (this.displayConfirmation === ZoomStatus.shown) {
      this.displayConfirmation = ZoomStatus.hidden;
    }
  }

  /**
   *
   * @param keyword Substring expected to be contained within the string
   * @param string String of characters which may or may not contain a keyword
   *
   * Returns true if the keyword's character in the given order are within the string
   */
  contains(keyword: string | undefined, string: string | undefined): boolean {
    if (keyword === undefined || string === undefined || keyword.length === 0) {
      return true;
    }
    if (keyword.length > string.length) {
      return false;
    }
    let k = 0;
    for (const entry of string) {
      if (entry.toLowerCase() === keyword[k].toLowerCase()) {
        k++;
      } else {
        k = 0;
      }
      if (k === keyword.length) {
        return true;
      }
    }
    return false;
  }

  public addRemoveSearchTechnology(technology: string): void {
    const tech: Technology | null = getTechnology(technology);
    if (tech === null) {
      return;
    }
    if (this.modelingToolSearch.technology === undefined) {
      this.modelingToolSearch.technology = [];
    }
    const index: number = this.modelingToolSearch.technology.indexOf(tech);
    if (index > -1) {
      this.modelingToolSearch.technology.splice(index, 1);
    } else {
      this.modelingToolSearch.technology.push(tech);
      this.modelingToolSearch.technology.sort();
    }
    this.searchModelingTool();
  }

  public addRemoveSearchModelingLanguage(language: string): void {
    if (this.modelingToolSearch.modelingLanguages === null || this.modelingToolSearch.modelingLanguages === undefined) {
      this.modelingToolSearch.modelingLanguages = [];
    }

    const index: number = this.modelingToolSearch.modelingLanguages.indexOf(language);
    if (index > -1) {
      this.modelingToolSearch.modelingLanguages.splice(index, 1);
    } else {
      this.modelingToolSearch.modelingLanguages.push(language);
      this.modelingToolSearch.modelingLanguages.sort();
    }
    this.searchModelingTool();
  }

  public addRemoveSearchPlatform(platform: string): void {
    if (this.modelingToolSearch.platform === null || this.modelingToolSearch.platform === undefined) {
      this.modelingToolSearch.platform = [];
    }

    const index: number = this.modelingToolSearch.platform.indexOf(platform);
    if (index > -1) {
      this.modelingToolSearch.platform.splice(index, 1);
    } else {
      this.modelingToolSearch.platform.push(platform);
      this.modelingToolSearch.platform.sort();
    }
    this.searchModelingTool();
  }

  public addRemoveSearchProgrammingLanguage(language: string): void {
    if (this.modelingToolSearch.programmingLanguage === null || this.modelingToolSearch.programmingLanguage === undefined) {
      this.modelingToolSearch.programmingLanguage = [];
    }

    if (language === 'C++') {
      language = 'C%2B%2B';
    }

    const index: number = this.modelingToolSearch.programmingLanguage.indexOf(language);
    if (index > -1) {
      this.modelingToolSearch.programmingLanguage.splice(index, 1);
    } else {
      this.modelingToolSearch.programmingLanguage.push(language);
      this.modelingToolSearch.programmingLanguage.sort();
    }
    this.searchModelingTool();
  }

  public searchTechStored(technology: string): boolean {
    const tech: Technology | null = getTechnology(technology);
    return tech !== null && this.modelingToolSearch.technology !== undefined && this.modelingToolSearch.technology.indexOf(tech) > -1;
  }

  public searchModelingLanguageStored(language: string): boolean {
    return this.modelingToolSearch.modelingLanguages !== null && this.modelingToolSearch.modelingLanguages !== undefined
      && this.modelingToolSearch.modelingLanguages.indexOf(language) > -1;
  }

  public searchPlatformStored(platform: string): boolean {
    return this.modelingToolSearch.platform !== null && this.modelingToolSearch.platform !== undefined
      && this.modelingToolSearch.platform.indexOf(platform) > -1;
  }

  public searchProgrammingLanguageStored(language: string): boolean {
    return this.modelingToolSearch.programmingLanguage !== null && this.modelingToolSearch.programmingLanguage !== undefined
      && this.modelingToolSearch.programmingLanguage.indexOf(language) > -1;
  }


  public getTechnologyEnum(): string[] {
    return getAllTechnologiesAsString();
  }

  public deleteSearchTech(tech: Technology): void {
    if (this.modelingToolSearch.technology !== undefined) {
      const index = this.modelingToolSearch.technology.indexOf(tech);
      if (index > -1) {
        this.modelingToolSearch.technology.splice(index, 1);
      }
    }
    this.searchModelingTool();
  }

  public deleteSearchModelingLanguage(language: string): void {
    if (this.modelingToolSearch.modelingLanguages !== undefined) {
      const index = this.modelingToolSearch.modelingLanguages.indexOf(language);
      if (index > -1) {
        this.modelingToolSearch.modelingLanguages.splice(index, 1);
      }
    }
    this.searchModelingTool();
  }

  public deleteSearchPlatform(platform: string): void {
    if (this.modelingToolSearch.platform !== undefined) {
      const index = this.modelingToolSearch.platform.indexOf(platform);
      if (index > -1) {
        this.modelingToolSearch.platform.splice(index, 1);
      }
    }
    this.searchModelingTool();
  }

  public deleteSearchProgrammingLanguage(language: string): void {
    if (this.modelingToolSearch.programmingLanguage !== undefined) {
      const index = this.modelingToolSearch.programmingLanguage.indexOf(language);
      if (index > -1) {
        this.modelingToolSearch.programmingLanguage.splice(index, 1);
      }
    }
    this.searchModelingTool();
  }

  public getCategoryAsString(category: string | undefined): string {
    return category === undefined || category === null ? 'Unknown' : getCategoryAsString(category);
  }

  public getTechnologyAsString(technology: string): string {
    const result = getTechnologyAsString(technology);
    return result === null ? '' : result;
  }

  public getLicenseAsString(license: string | undefined): string {
    return !license ? 'Unknown' : getLicenseAsString(license);
  }

  public getPlatformAsString(platform: string): string {
    const result = getPlatformAsString(platform);
    return result === null ? 'Unknown' : result;
  }

  public sortColumn(key: string): void {
    this.modelingToolsFiltered.sort(this.dynamicSort(key));
  }

  public selectedOrdered(property: string, ordered: number): boolean {
    return property === this.sortingKey && this.order === ordered;
  }

  public tableWidthHeight(): void {
    const table = document.querySelector('table');
    if (table !== null) {
      const tableDiv = document.getElementById('modeling-tools-column');
      if (tableDiv != null) {
        if (this.modelingToolsFiltered.length !== 0) {
          tableDiv.style.height = `${table.offsetHeight + 10 + 1}px`;
        } else {
          tableDiv.style.height = '200px';
        }
      }
    }
  }

  public zoomIn(src: string, alt: string, directionExpansion: DirectionExpansion): void {
    this.imgZoomedIn.src = src;
    this.imgZoomedIn.alt = alt;
    this.imgZoomedIn.directionExpansion = directionExpansion;
    this.imgZoomedIn.zoomedIn = ZoomStatus.initialized;
  }

  public zoomOut(): void {
    if (this.imgZoomedIn.zoomedIn > 1) {
      this.imgZoomedIn.zoomedIn = ZoomStatus.hidden;
    } else {
      this.imgZoomedIn.zoomedIn = ZoomStatus.shown;
    }
  }

  public optimizeColumns(): void {
    const windowWidth = window.innerWidth;
    if (this.filterDisplayed) {
      if (windowWidth >= 1780) {
        this.setCheckedColumns(true, true, true, true, true, true, true, true, true);
      }
      if (windowWidth < 1780 && windowWidth >= 1700) {
        this.setCheckedColumns(true, true, true, true, true, true, true, true);
      }
      if (windowWidth < 1700 && windowWidth >= 1560) {
        this.setCheckedColumns(true, true, true, true, true, true, true);
      }
      if (windowWidth < 1560 && windowWidth >= 1430) {
        this.setCheckedColumns(true, true, true, true, true, true);
      }
      if (windowWidth < 1430 && windowWidth >= 1290) {
        this.setCheckedColumns(true, true, true, true, true);
      }
      if (windowWidth < 1290 && windowWidth >= 1180) {
        this.setCheckedColumns(true, true, true, true);
      }
      if (windowWidth < 1180 && windowWidth >= 1060) {
        this.setCheckedColumns(true, true, true);
      }
      if (windowWidth < 1060 && windowWidth >= 950) {
        this.setCheckedColumns(true, true);
      }
      if (windowWidth < 950 && windowWidth >= 850) {
        this.setCheckedColumns(true);
      }
      if (windowWidth < 850) {
        this.setCheckedColumns();
      }
    }
  }

  public resetSearch(): void {
    const emptyProperties: boolean = Object.values(this.modelingToolSearch).every(el => el === undefined);
    if (!emptyProperties) {
      this.modelingToolSearch = {};
      this.searchModelingTool();
    }
  }

  private setCheckedColumns(openSource: boolean = false, technology: boolean = false, webApp: boolean = false,
                            desktopApp: boolean = false, category: boolean = false, modelingLanguages: boolean = false,
                            sourceCodeGeneration: boolean = false, cloudService: boolean = false,
                            license: boolean = false, loginRequired: boolean = false, realTimeCollab: boolean = false,
                            creator: boolean = false, platform: boolean = false, programmingLanguages: boolean = false) {
    this.checkedColumns = {
      openSource: openSource,
      appFramework: technology,
      webApp: webApp,
      desktopApp: desktopApp,
      category: category,
      modelingLanguages: modelingLanguages,
      sourceCodeGeneration: sourceCodeGeneration,
      cloudService: cloudService,
      license: license,
      loginRequired: loginRequired,
      realTimeCollab: realTimeCollab,
      creator: creator,
      os: platform,
      programmingLanguage: programmingLanguages
    };
  }

  private dynamicSort(property: string) {
    if (this.sortingKey !== property) {
      this.sortingKey = property;
      this.order = 1;
    } else {
      if (this.order === 1) {
        this.order = -1;
      } else {
        this.order = 1;
      }
    }
    let sortOrder: number = this.order;
    return function (a: ModelingTool, b: ModelingTool) {
      if ((a[property as keyof Object] === undefined || a[property as keyof Object] === null) && (b[property as keyof Object] === undefined || b[property as keyof Object] === null)) {
        return 0;
      }
      if (a[property as keyof Object] === undefined || a[property as keyof Object] === null) {
        return 1;
      }
      if (b[property as keyof Object] === undefined || b[property as keyof Object] === null) {
        return -1;
      }
      const result = (a[property as keyof Object] < b[property as keyof Object]) ? -1 : (a[property as keyof Object] > b[property as keyof Object]) ? 1 : 0;
      return result * sortOrder;
    }
}

  /**
   *
   * @param link to the website of the modeling tool provider
   *
   * Redirects a user to the page of the link he/she have clicked on
   */
  redirect(link: string): void {
    window.location.href = link;
  }
}

