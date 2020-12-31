import { AbstractControl, ValidatorFn } from '@angular/forms';


export function validateLength(field): ValidatorFn {
  return (c: AbstractControl): {[key: string]: any} | null => {
    const fieldLength = c.get(field).value ? c.get(field).value : '';
    let isEqual = false;
    if (fieldLength.length === 0 || fieldLength.length >= 8){
      isEqual = true;
    }
    return isEqual ? null : {testLength: {valid: false}};
  };
}
