export enum Category{
  graphical_modeling_tool = 'GRAPHICAL_MODELING_TOOL',
  business_tool = 'BUSINESS_TOOL',
  drawing_tool = 'DRAWING_TOOL',
  text_based_modeling_tool = 'TEXT_BASED_MODELING_TOOL',
  metamodeling_tool = 'METAMODELING_TOOL',
  mindmap_tool = 'MINDMAP_TOOL',
  mixed_textual_and_drawing_tool = 'MIXED_TEXTUAL_AND_DRAWING_TOOL',
  mixed_textual_and_graphical_modeling_tool = 'MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL'
}

export function getCategoryAsString(category: string): string {
  if (category === undefined || category === null) {
    return '';
  }
  switch (category.toLowerCase()) {
    case 'graphical_modeling_tool':
      return 'Graphical Modeling Tool';
    case 'business_tool':
      return 'Business Tool';
    case 'drawing_tool':
      return 'Drawing Tool';
    case 'text_based_modeling_tool':
      return 'Text-based Modeling Tool';
    case 'metamodeling_tool':
      return 'Metamodeling Tool';
    case 'mindmap_tool':
      return 'Mindmap Tool';
    case 'mixed_textual_and_drawing_tool':
      return 'Mixed Textual and Drawing Tool';
    case 'mixed_textual_and_graphical_modeling_tool':
      return 'Mixed Textual and Graphical Modeling Tool';
  }
  return '';
}
