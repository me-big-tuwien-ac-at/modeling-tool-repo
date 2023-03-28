export enum License {
  free = 'FREE',
  commercial = 'COMMERCIAL',
  restricted_free_and_commercial = 'RESTRICTED_FREE_AND_COMMERCIAL'
}
function getLicense(license: string): License | null {
  switch (license.toLowerCase()) {
    case 'free':
      return License.free;
    case 'commercial':
      return License.commercial;
    case 'free_and_commercial':
      return License.restricted_free_and_commercial;
  }
  return null;
}

export function getLicenseAsString(license: string): string {
  switch (license.toLowerCase()) {
    case 'free':
      return 'Free';
    case 'commercial':
      return 'Commercial';
    case 'restricted_free_and_commercial':
      return 'Restricted free content, commercial';
  }
  return '';
}
