import { AbstractControl, ValidatorFn } from '@angular/forms';


export function validateMatchPassword(field1, field2): ValidatorFn {
  return (c: AbstractControl): {[key: string]: any} | null => {
        const field1ToCompare = c.get(field1) ? c.get(field1).value : '';
        const field2ToCompare = c.get(field2) ? c.get(field2).value : '';
        const isEqual = field1ToCompare === field2ToCompare;

        return isEqual ? null : {testEqual: {valid: false}};
  };
}
