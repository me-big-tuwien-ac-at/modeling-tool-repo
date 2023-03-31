import {Component, OnInit} from '@angular/core';
import {ModelingTool} from '../dtos/modeling-tools';
import {UserModelValidator} from '../dtos/user-model-validator';
import {ModelingToolAndPropertiesAndEdit, ModelingToolsAndProperties} from '../dtos/modeling-tools-and-properties';
import {ModelingToolsService} from '../services/modeling-tools.service';
import {getAllTechnologiesAsString, getTechnology, getTechnologyAsString, Technology} from '../dtos/technology';
import {Category, getCategoryAsString} from '../dtos/category';
import {getLicenseAsString, License} from '../dtos/license';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {SharedService} from '../services/shared.service';
import {ThemeEnum} from '../dtos/theme';
import {ZoomStatus} from '../dtos/page-interaction-properties';


export enum CreateEditModelingToolsEnum {
  create,
  edit,
  editWithId
}

@Component({
  selector: 'app-create-edit-modeling-tools',
  templateUrl: './create-edit-modeling-tools.component.html',
  styleUrls: ['./create-edit-modeling-tools.component.scss']
})
export class CreateEditModelingToolsComponent implements OnInit {
  public modelingTools: ModelingTool[] = [];
  public modelingToolByUser: ModelingTool = {
    name: '',
    link: '',
    modelingLanguages: [],
    technology: [],
    platform: [],
    programmingLanguage: []
  };
  public originalModelingTool: ModelingTool = {
    name: '',
    link: '',
    modelingLanguages: [],
    technology: [],
    platform: [],
    programmingLanguage: []
  }
  public userModelValidator: UserModelValidator = {name: {}};
  public modelingLanguages: string[] = [];
  public suggestedModelingLanguage = '';
  public platforms: string[] = [];
  public suggestedPlatforms = '';
  public programmingLanguages: string[] = [];
  public suggestedProgrammingLanguage = '';

  // Checks for pop up windows
  public technologyChecked = false;
  public modelingLanguagesChecked = false;
  public creatorsChecked = false;
  public platformsChecked = false;
  public programmingLanguagesChecked = false;
  public creatorInput = '';

  // Captcha properties
  public siteKey: string = '6Ldw5bkkAAAAACArt28AdO4SWq6YtSbRlwaeHaTN';
  public resolvedByUser: string = '';

  public mode: CreateEditModelingToolsEnum = CreateEditModelingToolsEnum.create;

  public searchTerm: string = '';
  public searchTermForm = this.formBuilder.group({
    term: ''
  });

  public theme: ThemeEnum = ThemeEnum.light;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
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

  ngOnInit(): void {
    this.sharedService.setConfirmation(ZoomStatus.hidden);
    this.sharedService.aClickedEvent.subscribe((data: ThemeEnum) => console.log(data));
    this.setMode();
  }

  /**
   * Sets the header of the page depending on whether the user is suggesting a new modeling tool or an edit.
   */
  public get heading(): string {
    switch (this.mode) {
      case CreateEditModelingToolsEnum.create:
        return 'Suggest a new Modeling Tool';
      case CreateEditModelingToolsEnum.edit:
        return 'Search for a Modeling Tool';
      case CreateEditModelingToolsEnum.editWithId:
        return 'Suggest a Modeling Tool Edit';
      default:
        return '?';
    }
  }

  /**
   * Submits a new modeling tool suggestion or a modeling tool edit suggestion.
   */
  public onSubmit(): void {
    this.userSuggestionEditValidation();
    if (this.isModelingToolValid()) {
      if (this.modelingToolByUser.id !== undefined) {
        this.service.sendModelingToolEdit(this.modelingToolByUser, this.modelingToolByUser.id).subscribe({
          next: () => {
            console.log('Modeling tool edit suggestion successfully sent!');
          },
          error: err => {
            console.log(err);
          }
        })
      } else {
        this.service.sendModelingTool(this.modelingToolByUser).subscribe({
          next: () => {
            console.log('Modeling tool suggestion successfully sent!');
          },
          error: err => {
            console.log(err);
          }
        });
      }
      this.sharedService.setConfirmation(ZoomStatus.initialized);
      this.router.navigate(['/']);
    }
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
   * Searches for a modeling tool that has a name into which the characters in the given order of a search term fit into.
   */
  public searchModelingTool(): void {
    const term = this.searchTermForm.controls.term.value;
    if (term !== undefined && term !== null && term.length !== 0) {
      this.service.searchModelingToolByName(term).subscribe({
        next: (data: ModelingTool[]) => {
          this.modelingTools = data;
          this.searchTerm = term;
          this.sharedService.setTheme();
        },
        error: err => {
          console.log(`Error fetching modeling tools with name: ${term}`, err);
        }
      });
    }
  }

  /**
   * Loads all verified modeling tools, modeling languages, platforms and programming languages from the backend.
   * @private
   */
  private loadModelingToolsAndProperties() {
    this.service.getModelingToolsAndProperties().subscribe({
      next: (data: ModelingToolsAndProperties) => {
        this.modelingTools = data.modelingTools;
        this.modelingLanguages = data.modelingLanguages;
        this.platforms = data.platforms;
        this.programmingLanguages = data.programmingLanguages;
      },
      error: err => {
        console.log('Error fetching modeling tools and properties', err);
        if (err.status === 0) {
          console.log('Is the backend up?');
        }
      }
    });
  }

  /**
   * Loads all verified modeling tools, modeling tool with id, modeling languages, platforms and programming languages
   * from the backend.
   * @private
   */
  private loadModelingToolEditSuggestions() {
    let id: number = -1;
    this.route.params.subscribe((params: Params) => id = params['id']);
    this.service.getModelingToolsAndModelingToolById(id).subscribe({
      next: (data: ModelingToolAndPropertiesAndEdit) => {
        this.modelingTools = data.modelingTools;
        this.modelingToolByUser = data.modelingTool;
        if (this.mode === CreateEditModelingToolsEnum.editWithId) {
          this.originalModelingTool = JSON.parse(JSON.stringify(data.modelingTool));
        }
        this.modelingLanguages = data.modelingLanguages;
        this.platforms = data.platforms;
        this.programmingLanguages = data.programmingLanguages;
      },
      error: err => {
        console.log('Error fetching modeling tools and properties', err);
        if (err.status === 0) {
          console.log('Is the backend up?');
        }
      }
    });
  }

  private setMode(): void {
    const url = this.router.url;
    if (url.match(/.*(modeling_tool\/create)/g)) {
      this.mode = CreateEditModelingToolsEnum.create;
      this.loadModelingToolsAndProperties();
    }
    if (url.match(/.*(modeling_tool\/edit$)/g)) {
      this.mode = CreateEditModelingToolsEnum.edit;
      this.searchModelingTool();
    }
    if (url.match(/.*(modeling_tool\/edit\/\d+)/g)) {
      this.mode = CreateEditModelingToolsEnum.editWithId;
      this.loadModelingToolEditSuggestions();
    }
  }

  /**
   * Evaluates the validity of inputs for a modeling tool that a user suggested.
   */
  private userSuggestionEditValidation(): void {
    this.userModelValidator = {name: {}};
    const userSuggestion = this.modelingToolByUser;

    // Name validation
    this.userModelValidator.name.syntactic = this.checkIfStringIsValid(userSuggestion.name, 'Modeling Tool name');
    this.checkIfToolNameAlreadyExists();

    // Link Validation
    this.userModelValidator.link = this.checkIfStringIsValid(userSuggestion.link, 'Link')
    if (this.userModelValidator.link === undefined && !userSuggestion.link.includes('.')) {
      this.userModelValidator.link = 'Please provide a valid link';
    }

    // Email Validation
    if (!userSuggestion.userEmail) {
      this.userModelValidator.email = 'Please provide your Email address';
    } else {
      this.userModelValidator.email = this.checkIfStringIsValid(userSuggestion.userEmail, 'Email address');
      if (this.userModelValidator.email === undefined) {
        const emailRegex = /^[\w.]+@\w+\.[\w.]+$/g
        if (!emailRegex.test(userSuggestion.userEmail)) {
          this.userModelValidator.email = 'Please provide a valid Email address.'
        }
      }
    }

    // Checking whether the original modeling tool and the modeling tool edit by the user are equal
    if (this.mode === CreateEditModelingToolsEnum.editWithId && this.areToolsAreEqual(this.modelingToolByUser, this.originalModelingTool)) {
      this.userModelValidator.toolNotEdited = 'You cannot submit a Modeling Tool with the same values as an edit.';
    }
  }

  /**
   * Checks whether a Modeling Tool with a given name already exists.
   */
  public checkIfToolNameAlreadyExists(): void {
    const userSuggestion: ModelingTool = this.modelingToolByUser;
    this.userModelValidator.name.duplicate = undefined;

    if (userSuggestion.name !== undefined && userSuggestion.name !== null && userSuggestion.name !== '' && !userSuggestion.id) {
      this.modelingTools.forEach((tool: ModelingTool) => {
        if (tool.name.toLowerCase() === userSuggestion.name.toLowerCase()) {
          this.userModelValidator.name.duplicate = {
            id: tool.id !== undefined ? tool.id : -1,
            name: tool.name
          }
          this.userModelValidator.name.syntactic = undefined;
        }
      });
    }
  }

  /**
   * Compares if two modeling tools contain the same values independent of order or letter casing.
   *
   * @param tool1 First Modeling Tool
   * @param tool2 Second Modeling Tool
   * @private
   */
  private areToolsAreEqual(tool1: ModelingTool, tool2: ModelingTool): boolean {
    if (
      tool1.name.toLowerCase().trim() === tool2.name.toLowerCase().trim() &&
      tool1.link.toLowerCase().trim() === tool2.link.toLowerCase().trim() &&
      tool1.openSource === tool2.openSource &&
      this.arraysEqual(tool1.technology, tool2.technology) &&
      tool1.webApp === tool2.webApp &&
      tool1.desktopApp === tool2.desktopApp &&
      tool1.category === tool2.category &&
      this.arraysEqual(tool1.modelingLanguages, tool2.modelingLanguages) &&
      tool1.sourceCodeGeneration === tool2.sourceCodeGeneration &&
      tool1.cloudService === tool2.cloudService &&
      tool1.license === tool2.license &&
      tool1.loginRequired === tool2.loginRequired &&
      tool1.realTimeCollab === tool2.realTimeCollab &&
      this.arraysEqual(tool1.creator, tool2.creator) &&
      this.arraysEqual(tool1.platform, tool2.platform) &&
      this.arraysEqual(tool1.programmingLanguage, tool2.programmingLanguage) &&
      (tool1.feedback === null || tool1.feedback === undefined || tool1.feedback.length === 0)
    ) {
      return true;
    }
    return false;
  }

  /**
   * Compares if two arrays are equal independent of order or letter casing.
   * @param arr1 First array
   * @param arr2 Second array
   */
  public arraysEqual(arr1: string[] | undefined, arr2: string[] | undefined): boolean {
    if (!arr1 && !arr2) {
      return true;
    }
    if (!arr1 || !arr2) {
      return false;
    }
    arr1 = arr1.map(el => el.toString().toLowerCase().trim()).sort();
    arr2 = arr2.map(el => el.toString().toLowerCase().trim()).sort();
    return arr1.join(',') === arr2.join(',');
  }

  public setUserToolArray(key: string, arr: string[] | Technology[] | undefined): void {
    if (key === 'technology' || key === 'modelingLanguages' || key === 'creator' || key === 'platform' || key === 'programmingLanguage') {
      this.modelingToolByUser[key] = JSON.parse(JSON.stringify(arr));
    }
  }

  /**
   * Checks if a Modeling Tool is valid.
   * @private
   */
  private isModelingToolValid(): boolean {
    const validator: UserModelValidator = this.userModelValidator;
    return !validator.name.duplicate && !validator.name.syntactic && !validator.link && !validator.toolNotEdited && !validator.email;
  }

  /**
   * Evaluate whether a string input within a modeling tool suggestion is valid.
   *
   * @param input input string by the user
   * @param inputType for which input section is the input being evaluated
   *
   * @private
   */
  private checkIfStringIsValid(input: string, inputType: string): string | undefined {
    // Name validation
    if (input.trim().length === 0) {
      return `Please provide a valid ${inputType}.`;
    }
    if (input.trim().length > 255) {
      return `${inputType} should contain less than 255 characters.`;
    }
    return undefined;
  }

  // Methods below: Use a separate typescript file which contains these copy/paste methods?
  public deleteTech(tech: Technology): void {
    const index = this.modelingToolByUser.technology.indexOf(tech);
    if (index > -1) {
      this.modelingToolByUser.technology.splice(index, 1);
    }
  }

  /**
   * Deletes a modeling language from the array of modeling languages suggested to a tool by the user.
   *
   * @param language modeling language suggested to a modeling tool
   */
  public deleteModelingLanguage(language: string): void {
    const index = this.modelingToolByUser.modelingLanguages.indexOf(language);
    if (index > -1) {
      this.modelingToolByUser.modelingLanguages.splice(index, 1);
    }
  }

  public addCreator(): void {
    if (this.creatorInput.length > 0) {
      if (this.modelingToolByUser.creator) {
        const creatorIndex: number = this.modelingToolByUser.creator.findIndex(c => c.toLowerCase() === this.creatorInput.toLowerCase());
        if (creatorIndex <= -1) {
          this.modelingToolByUser.creator.push(this.creatorInput);
        }
      } else {
        this.modelingToolByUser.creator = [this.creatorInput];
      }
      this.creatorInput = '';
      this.creatorsChecked = false;
    }
  }

  public deleteCreator(creator: string): void {
    if (this.modelingToolByUser.creator) {
      const index = this.modelingToolByUser.creator.indexOf(creator);
      if (index > -1) {
        this.modelingToolByUser.creator.splice(index, 1);
      }
    }
  }

  /**
   * Deletes a platform from the array of platform suggested to a tool by the user.
   *
   * @param platform platform suggested to a modeling tool
   */
  public deletePlatform(platform: string): void {
    const index = this.modelingToolByUser.platform.indexOf(platform);
    if (index > -1) {
      this.modelingToolByUser.platform.splice(index, 1);
    }
  }

  /**
   * Deletes a programming language from the array of programming languages suggested to a tool by the user.
   *
   * @param language programming language suggested to a modeling tool
   */
  public deleteLanguage(language: string): void {
    const index = this.modelingToolByUser.programmingLanguage.indexOf(language);
    if (index > -1) {
      this.modelingToolByUser.programmingLanguage.splice(index, 1);
    }
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

  public userTechStored(technology: string): boolean {
    const tech: Technology | null = getTechnology(technology);
    return tech !== null && this.modelingToolByUser.technology.indexOf(tech) > -1;
  }

  /**
   * Checks if a platform is already contained among an array of platforms contained within a
   *  modeling tool suggestion.
   *
   * @param platform platform
   */
  public userPlatformStored(platform: string): boolean {
    return this.modelingToolByUser.platform !== null && this.modelingToolByUser.platform !== undefined
      && this.modelingToolByUser.platform.indexOf(platform) > -1;
  }

  /**
   * Checks if a modeling language is already contained among an array of modeling languages contained within a
   *  modeling tool suggestion.
   *
   * @param language modeling language
   */
  public userModelingLanguageStored(language: string): boolean {
    return this.modelingToolByUser.modelingLanguages !== null && this.modelingToolByUser.modelingLanguages !== undefined
      && this.modelingToolByUser.modelingLanguages.indexOf(language) > -1;
  }

  /**
   * Checks if a programming language is already contained among an array of programming languages contained within a
   *  modeling tool suggestion.
   *
   * @param language modeling language
   */
  public userPlStored(language: string): boolean {
    return this.modelingToolByUser.programmingLanguage !== null && this.modelingToolByUser.programmingLanguage !== undefined
      && this.modelingToolByUser.programmingLanguage.indexOf(language) > -1;
  }

  public getCategoryEnum(): string[] {
    let categoryStrings: any[] = [null];
    Object.keys(Category).forEach((cat: string) => {
      categoryStrings.push(Category[cat as keyof Object]);
    });
    return categoryStrings;
  }

  public getTechnologyEnum(): string[] {
    return getAllTechnologiesAsString();
  }

  public getLicenseEnum(): string[] {
    let licenseStrings: any[] = [null];
    Object.keys(License).forEach((license: string) => {
      licenseStrings.push(License[license as keyof Object]);
    })
    return licenseStrings;
  }

  public addRemoveTechnology(technology: string): void {
    const tech: Technology | null = getTechnology(technology);
    if (tech === null) {
      return;
    }
    const index: number = this.modelingToolByUser.technology.indexOf(tech);
    if (index > -1) {
      this.modelingToolByUser.technology.splice(index, 1);
    } else {
      this.modelingToolByUser.technology.push(tech);
      this.modelingToolByUser.technology.sort();
    }
  }

  /**
   * Adds or removes a modeling language from the list of modeling languages within the users modeling tool suggestion.
   *
   * @param language modeling language added or removed by the user
   */
  public addRemoveModelingLanguage(language: string): void {
    if (this.modelingToolByUser.modelingLanguages === null) {
      this.modelingToolByUser.modelingLanguages = [];
    }

    const userMlIndex: number = this.modelingToolByUser.modelingLanguages.findIndex(item => language.toLowerCase() === item.toLowerCase());
    const existingMlIndex: number = this.modelingLanguages.findIndex(item => language.toLowerCase() === item.toLowerCase());

    if (existingMlIndex > -1) {
      if (userMlIndex > -1) {
        if (this.suggestedModelingLanguage.length > 0) {
          this.userModelValidator.modelingLanguage = 'Modeling Language suggestion already saved!';
        } else {
          this.modelingToolByUser.modelingLanguages.splice(userMlIndex, 1);
          this.userModelValidator.modelingLanguage = '';
        }
      } else {
        this.modelingToolByUser.modelingLanguages.push(this.modelingLanguages[existingMlIndex]);
        this.modelingToolByUser.modelingLanguages.sort();
        this.userModelValidator.modelingLanguage = '';
      }
    } else {
      if (userMlIndex > -1) {
        this.userModelValidator.modelingLanguage = 'Modeling Language suggestion already saved!';
      } else {
        if (language.length > 0) {
          this.modelingToolByUser.modelingLanguages.push(language);
          this.modelingToolByUser.modelingLanguages.sort();
          this.userModelValidator.modelingLanguage = '';
        }
      }
    }
    this.suggestedModelingLanguage = '';
  }

  /**
   * Adds or removes a platform from the list of platforms within the users modeling tool suggestion.
   *
   * @param platform platform added or removed by the user
   */
  public addRemovePlatform(platform: string): void {
    if (this.modelingToolByUser.platform === null) {
      this.modelingToolByUser.platform = [];
    }

    const userPlatformIndex: number = this.modelingToolByUser.platform.findIndex(item => platform.toLowerCase() === item.toLowerCase());
    const existingPlatformIndex: number = this.platforms.findIndex(item => platform.toLowerCase() === item.toLowerCase());

    if (existingPlatformIndex > -1) {
      if (userPlatformIndex > -1) {
        if (this.suggestedPlatforms.length > 0) {
          this.userModelValidator.platform = 'Platform suggestion already saved!';
        } else {
          this.modelingToolByUser.platform.splice(userPlatformIndex, 1);
          this.userModelValidator.platform = '';
        }
      } else {
        this.modelingToolByUser.platform.push(this.platforms[existingPlatformIndex]);
        this.modelingToolByUser.platform.sort();
        this.userModelValidator.platform = '';
      }
    } else {
      if (userPlatformIndex > -1) {
        this.userModelValidator.platform = 'Platform suggestion already saved!';
      } else {
        if (platform.length > 0) {
          this.modelingToolByUser.platform.push(platform);
          this.modelingToolByUser.platform.sort();
          this.userModelValidator.platform = '';
        }
      }
    }
    this.suggestedPlatforms = '';
  }

  /**
   * Adds or removes a programming language from the list of programming languages within the users modeling tool suggestion.
   *
   * @param language programming language added or removed by the user
   */
  public addRemoveProgrammingLanguage(language: string): void {
    if (this.modelingToolByUser.programmingLanguage === null) {
      this.modelingToolByUser.programmingLanguage = [];
    }

    const userPlIndex: number = this.modelingToolByUser.programmingLanguage.findIndex(item => language.toLowerCase() === item.toLowerCase());
    const existingPlIndex: number = this.programmingLanguages.findIndex(item => language.toLowerCase() === item.toLowerCase());

    if (existingPlIndex > -1) {
      if (userPlIndex > -1) {
        if (this.suggestedProgrammingLanguage.length > 0) {
          this.userModelValidator.programmingLanguage = 'Programming Language suggestion already saved!';
        } else {
          this.modelingToolByUser.programmingLanguage.splice(userPlIndex, 1);
          this.userModelValidator.programmingLanguage = '';
        }
      } else {
        this.modelingToolByUser.programmingLanguage.push(this.programmingLanguages[existingPlIndex]);
        this.modelingToolByUser.programmingLanguage.sort();
        this.userModelValidator.programmingLanguage = '';
      }
    } else {
      if (userPlIndex > -1) {
        this.userModelValidator.programmingLanguage = 'Programming Language suggestion already saved!';
      } else {
        if (language.length > 0) {
          this.modelingToolByUser.programmingLanguage.push(language);
          this.modelingToolByUser.programmingLanguage.sort();
          this.userModelValidator.programmingLanguage = '';
        }
      }
    }
    this.suggestedProgrammingLanguage = '';
  }
}
