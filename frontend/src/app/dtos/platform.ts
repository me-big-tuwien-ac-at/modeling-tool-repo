export enum Platform {
  chrome = 'CHROME',
  edge = 'EDGE',
  firefox = 'FIREFOX',
  linux = 'LINUX',
  macos = 'MACOS',
  solaris = 'SOLARIS',
  unix = 'UNIX',
  windows = 'WINDOWS'
}

export function getPlatformAsString(platform: string): string | null {
  switch (platform.toLowerCase()) {
    case 'chrome':
      return 'Chrome';
    case 'edge':
      return 'Edge';
    case 'firefox':
      return 'Firefox';
    case 'linux':
      return 'Linux';
    case 'macos':
      return 'MacOs';
    case 'solaris':
      return 'Solaris';
    case 'unix':
      return 'Unix';
    case 'windows':
      return 'Windows'
  }
  return null;
}
