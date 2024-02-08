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

modeling_tool = {
  name: "",
  link: ""
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
  modeling_tool.name = e.target.value;
});
document.getElementById('tool_link').addEventListener('input', (e) => {
  modeling_tool.link = e.target.value;
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
    modeling_tool_validator.name = checkIfStringIsValid(modeling_tool.name);
  }

  // Check if name is not a duplicate
  // Check if link is provided
  modeling_tool_validator.link = isValidUrl(modeling_tool.link);

  console.log("Submitting modeling tool");
  console.log(modeling_tool);
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
  modeling_tool_validator.name.syntactic = checkIfStringIsValid(modeling_tool.name);
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
