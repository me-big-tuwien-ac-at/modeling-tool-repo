export enum Technology {
  APP = 'APP',
  FRAMEWORK = 'FRAMEWORK',
  LIBRARY = 'LIBRARY'
}
export function getTechnology(technology: string): Technology | null {
  switch (technology.toLowerCase()) {
    case 'app':
      return Technology.APP;
    case 'framework':
      return Technology.FRAMEWORK;
    case 'library':
      return Technology.LIBRARY;
  }
  return null;
}

export function getAllTechnologies(): Technology[] {
  return [
    Technology.APP,
    Technology.FRAMEWORK,
    Technology.LIBRARY
  ];
}

export function getTechnologyAsString(technology: string | Technology): string | null {
  switch (technology.toString().toLowerCase()) {
    case 'app':
      return 'App'
    case 'framework':
      return 'Framework'
    case 'library':
      return 'Library'
  }
  return null;
}

export function getAllTechnologiesAsString(): string[] {
  let categoryStrings: any[] = [];
  Object.keys(Technology).forEach((technology: string) => {
    categoryStrings.push(Technology[technology as keyof Object]);
  })
  return categoryStrings;
}
