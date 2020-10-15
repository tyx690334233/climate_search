import { Directive } from '@angular/core';
import { NG_VALIDATORS, Validator, ValidatorFn, AbstractControl } from "@angular/forms";


export function ValidationDirective(control: AbstractControl) {
  let isWhitespace = (control.value || "").trim().length === 0;
  return isWhitespace ? { invalidUrl: true } : null;
}