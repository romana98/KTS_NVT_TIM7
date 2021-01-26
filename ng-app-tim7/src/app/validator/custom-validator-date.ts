import { AbstractControl, ValidatorFn } from '@angular/forms';

// date1 is start date, date2 is end date
export function validateDate(date1, date2): ValidatorFn {
  return (c: AbstractControl): {[key: string]: any} | null => {
    const field1ToCompare = c.get(date1) ? c.get(date1).value : 0;
    const field2ToCompare = c.get(date2) ? c.get(date2).value : 0;

    return field1ToCompare > field2ToCompare ? {testBefore: {valid: false}} : null;
  };
}

