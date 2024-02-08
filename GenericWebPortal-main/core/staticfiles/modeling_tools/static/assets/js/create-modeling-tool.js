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
      break;
    }
  }
  if (name_match) {
    nameDuplicateWarning.style.display = null;
    tool_name_input.classList.add('invalid-input-border');
  } else {
    nameDuplicateWarning.style.display = 'none';
    tool_name_input.classList.remove('invalid-input-border');
  }
});
