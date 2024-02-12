const modeling_tool_names_raw = document.getElementById("helper").getAttribute("data-name");
const modeling_languages_raw = document.getElementById("helper").getAttribute("data-model-name");
const platforms_raw = document.getElementById("helper").getAttribute("data-height");
const programming_languages_raw = document.getElementById("helper").getAttribute("data-hidden");

const modeling_tools = extract_regex_matches(modeling_tool_names_raw, /'(.*?)'/gm, 1);
const modeling_languages = extract_regex_matches(modeling_languages_raw, /'(.*?)'/gm, 1);
const platforms = extract_regex_matches(platforms_raw, /'(.*?)'/gm, 1);
const programming_languages = extract_regex_matches(programming_languages_raw, /'(.*?)'/gm, 1);

function extract_regex_matches(input, regex, group) {
  const result = []
  for (const match of input.matchAll(regex)) {
    const reg_match = match[group];
    result.push(reg_match);
  }
  return result
}

userModelingTool = {
  name: "",
  link: "",
  technologies: []
}

modeling_tool_validator = {
  name: {
    syntactic: undefined,
    duplicate: undefined
  },
  toolNotEdited: undefined,
  link: undefined,
  email: undefined,
  modelingLanguage: undefined,
  platform: undefined,
  programmingLanguage: undefined
}

/***********************************
 LISTENERS ASSIGNING MODELING TOOL VALUES
************************************/
document.getElementById('tool_name').addEventListener('input', (e) => {
  userModelingTool.name = e.target.value;
});
document.getElementById('tool_link').addEventListener('input', (e) => {
  userModelingTool.link = e.target.value;
});

/***********************************
 VERIFYING IF MODELING TOOL NAME ALREADY EXISTS
************************************/
const tool_name_input = document.getElementById('tool_name');
tool_name_input.addEventListener('input', (event) => {
  const user_tool_name = event.target.value;
  const nameDuplicateWarning = document.getElementById('invalid-input-name-duplicate');
  let name_match = false;

  for (let i = 0; i < modeling_tools.length; i++) {
    if (modeling_tools[i].trim().toLowerCase() === user_tool_name.toLowerCase()) {
      name_match = true;
      modeling_tool_validator.name = modeling_tools[i];
      break;
    }
  }
  if (name_match) {
    document.getElementById('name-duplicate-name').innerText = modeling_tool_validator.name;
    nameDuplicateWarning.style.display = null;
    tool_name_input.classList.add('invalid-input-border');
  } else {
    nameDuplicateWarning.style.display = 'none';
    modeling_tool_validator.name = undefined;
    tool_name_input.classList.remove('invalid-input-border');
  }
});

/***********************************
 VERIFYING IF REQUIRED FIELDS ARE FILLED OUT
************************************/
function submitModelingTool() {
  // Check if the name of the modeling tool is not a duplicate, if it is not, check if the string is valid
  if (modeling_tool_validator.name !== undefined) {
    // Check if name is provided
    modeling_tool_validator.name = checkIfStringIsValid(userModelingTool.name);
  }

  // Check if name is not a duplicate
  // Check if link is provided
  modeling_tool_validator.link = isValidUrl(userModelingTool.link);

  console.log("Submitting modeling tool");
  console.log(userModelingTool);
}

function isModelingToolValid() {
  const tool_valid =
      modeling_tool_validator.name.syntactic === undefined &&
      modeling_tool_validator.name.duplicate === undefined &&
      modeling_tool_validator.link === undefined &&
      modeling_tool_validator.modelingLanguage === undefined &&
      modeling_tool_validator.platform === undefined &&
      modeling_tool_validator.programmingLanguage === undefined;

  if (tool_valid) {
    return true;
  }

  // TODO: 1. Check if a modeling tool with the same name already exists
  checkIfModelingToolNameIsADuplicate();
  if (modeling_tool_validator.name.duplicate !== undefined) {
    console.log("Modeling Tool with same name already exists");
  }

  // TODO: 2. Check if the modeling tool is syntactically sound
  modeling_tool_validator.name.syntactic = checkIfStringIsValid(userModelingTool.name);
}

function checkIfStringIsValid(input, inputType) {
  // Name validation
  if (input.trim().length === 0) {
    return `Please provide a valid ${inputType}.`;
  }
  if (input.trim().length > 255) {
    return `${inputType} should contain less than 255 characters.`;
  }
  return undefined;
}

function checkIfModelingToolNameIsADuplicate() {
  for (let i = 0; i < modeling_tools.length; i++) {
    const user_tool_name = document.getElementById('tool_name').value;
    if (modeling_tools[i].trim().toLowerCase() === user_tool_name.toLowerCase()) {
      modeling_tool_validator.name.duplicate = modeling_tools[i];
      break;
    }
  }
  modeling_tool_validator.name.duplicate = undefined;
}

const isValidUrl = urlString => {
    const urlPattern = new RegExp('^(https?:\\/\\/)?'+ // validate protocol
    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // validate domain name
    '((\\d{1,3}\\.){3}\\d{1,3}))'+ // validate OR ip (v4) address
    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // validate port and path
    '(\\?[;&a-z\\d%_.~+=-]*)?'+ // validate query string
    '(\\#[-a-z\\d_]*)?$','i'); // validate fragment locator
  return !!urlPattern.test(urlString);
}

/***********************************
 COLLAPSING/EXPANDING PROPERTY OPTIONS
************************************/
const technologyInput = document.getElementById('technology-input');
const technologyOptions = document.getElementById('appLibraryFramework');
document.addEventListener('click', function(event) {
  const isClickInside = technologyInput.contains(event.target);
  const ulList = technologyOptions.children[0];
  if (ulList.contains(event.target)) {
    return;
  }
  const multItemList = document.getElementsByClassName('mult-item');
  if (multItemList.length > 0) {
    for (let i = 0; i < multItemList.length; i++) {
      if (multItemList[i].contains(event.target)) {
        const value = multItemList[i].children[0].children[0].outerText;
        userModelingTool.technologies = userModelingTool.technologies.filter((tech) => {
          return tech !== value;
        });
        multItemList[i].remove();
        return;
      }
    }
  }
  const techDisplay = technologyOptions.style.display;
  if (isClickInside) {
    if (techDisplay === 'none') {
      technologyOptions.style.display = null;
    } else {
      technologyOptions.style.display = 'none';
    }
  }
  else {
    technologyOptions.style.display = 'none';
  }
});

const modelingLanguagesInput = document.getElementById('modeling-languages-input');
const modelingLanguageOptions = document.getElementById('modelingLanguagesSuggestion');
document.addEventListener('click', function(event) {
  const isClickInside = modelingLanguagesInput.contains(event.target);
  const ulList = modelingLanguageOptions.children[0];
  if (ulList.contains(event.target)) {
    return;
  }
  const modelingLanguagesDisplay = modelingLanguageOptions.style.display;
  if (isClickInside) {
    if (modelingLanguagesDisplay === 'none') {
      modelingLanguageOptions.style.display = null;
    } else {
      modelingLanguageOptions.style.display = 'none';
    }
  }
  else {
    modelingLanguageOptions.style.display = 'none';
  }
});

const creatorsInput = document.getElementById('creators-input');
const creatorsOptions = document.getElementById('creator');
document.addEventListener('click', function(event) {
  const isClickInside = creatorsInput.contains(event.target);
  const ulList = creatorsOptions.children[0];
  if (ulList.contains(event.target)) {
    return;
  }
  const creatorsDisplay = creatorsOptions.style.display;
  if (isClickInside) {
    if (creatorsDisplay === 'none') {
      creatorsOptions.style.display = null;
    } else {
      creatorsOptions.style.display = 'none';
    }
  }
  else {
    creatorsOptions.style.display = 'none';
  }
});

const platformsInput = document.getElementById('platform-input');
const platformsOptions = document.getElementById('editPlatforms');
document.addEventListener('click', function(event) {
  const isClickInside = platformsInput.contains(event.target);
  const ulList = platformsOptions.children[0];
  if (ulList.contains(event.target)) {
    return;
  }
  const platformsDisplay = platformsOptions.style.display;
  if (isClickInside) {
    if (platformsDisplay === 'none') {
      platformsOptions.style.display = null;
    } else {
      platformsOptions.style.display = 'none';
    }
  }
  else {
    platformsOptions.style.display = 'none';
  }
});

const programmingLanguagesInput = document.getElementById('programming-language-input');
const programmingLanguagesOptions = document.getElementById('programmingLanguages');
document.addEventListener('click', function(event) {
  const isClickInside = programmingLanguagesInput.contains(event.target);
  const ulList = programmingLanguagesOptions.children[0];
  if (ulList.contains(event.target)) {
    return;
  }
  const programmingLanguagesDisplay = programmingLanguagesOptions.style.display;
  if (isClickInside) {
    if (programmingLanguagesDisplay === 'none') {
      programmingLanguagesOptions.style.display = null;
    } else {
      programmingLanguagesOptions.style.display = 'none';
    }
  }
  else {
    programmingLanguagesOptions.style.display = 'none';
  }
});

/***********************************
 PROPERTY OPTION CSS
************************************/
const selectFields = document.getElementsByClassName('form-select');
for (let i = 0; i < selectFields.length; i++) {
  const selectField = selectFields[i];
  selectField.addEventListener('change', (event) => {
    const selectValue = event.target.value;
    const selectClassList = event.target.classList;
    if (selectValue === null || selectValue === undefined || selectValue.length === 0) {
      selectClassList.remove('false-value');
      selectClassList.remove('true-value');
    } else if (selectValue === 'No') {
      selectClassList.remove('true-value');
      selectClassList.add('false-value');
    } else {
      selectClassList.remove('false-value');
      selectClassList.add('true-value');
    }
  });
}

/***********************************
 ADD TECHNOLOGY TO USER MODELING TOOL
************************************/
const technologySection = document.getElementById('appLibraryFramework');
const technologyListItems = technologySection.children[0].children;
for (let i = 0; i < technologyListItems.length; i++) {
  technologyListItems[i].addEventListener('click', () => {
    const value = technologyListItems[i].children[1].outerText;
    if (userModelingTool.technologies.includes(value)) {
      userModelingTool.technologies = userModelingTool.technologies.filter((tech) => {
        return tech !== value;
      });
      setSelectedProperties(technologyInput, userModelingTool.technologies);
      const checkbox = document.getElementById(`item-${value.toLowerCase()}`).children[0];
      checkbox.classList.remove('checked');
      const checkedIcon = document.getElementById(`check-${value.toLowerCase()}`);
      checkedIcon.style.display = 'none';
    } else {
      userModelingTool.technologies.push(value);
      setSelectedProperties(technologyInput, userModelingTool.technologies);
      const checkbox = document.getElementById(`item-${value.toLowerCase()}`).children[0];
      checkbox.classList.add('checked');
      const checkedIcon = document.getElementById(`check-${value.toLowerCase()}`);
      checkedIcon.style.display = null;
    }
  });
}

function setSelectedProperties(propertySection, userProperties) {
  let techInnerHtml = ``;
  for (let j = 0; j <userProperties.length; j++) {
    techInnerHtml +=  `
    <div class="mult-item">
      <span>
        <span>${userProperties[j]}</span>
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
          <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"></path>
        </svg>
      </span>
    </div>
  `;
  }
  propertySection.innerHTML = techInnerHtml;
}

const multItems = document.getElementsByClassName('mult-item');
for (let i = 0; i < multItems.length; i++) {
  multItems[i].addEventListener('click', () => {
    multItems[i].remove();
  })
}