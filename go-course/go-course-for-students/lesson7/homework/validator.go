package homework

import (
	"github.com/pkg/errors"
	"reflect"
	"strconv"
	"strings"
)

var ErrNotStruct = errors.New("wrong argument given, should be a struct")
var ErrInvalidValidatorSyntax = errors.New("invalid validator syntax")
var ErrValidateForUnexportedFields = errors.New("validation for unexported field is not allowed")

type ValidationError struct {
	FieldName string
	Err       error
}

type ValidationErrors []ValidationError

func (ve ValidationErrors) Error() string {
	resultError := strings.Builder{}

	if len(ve) == 1 {
		return ve[0].Err.Error()
	}

	for _, validationError := range ve {
		resultError.WriteString(validationError.FieldName + ": " + validationError.Err.Error() + "\n")
	}

	return resultError.String()
}

func Validate(v any) error {
	refVal := reflect.ValueOf(v)

	if refVal.Type().Kind() != reflect.Struct {
		return ErrNotStruct
	}

	ve := make(ValidationErrors, 0)

	for i := 0; i < refVal.Type().NumField(); i++ {
		typeField := refVal.Type().Field(i)

		vRulesStr := typeField.Tag.Get("validate")
		if vRulesStr == "" {
			continue
		}

		if !typeField.IsExported() {
			ve = append(ve, ValidationError{Err: ErrValidateForUnexportedFields})
			return ve
		}

		field := refVal.Field(i)
		vRules := strings.Split(vRulesStr, "|")

		for _, r := range vRules {
			if err := doValidate(typeField.Type.Kind(), field, r); err != nil {
				if errors.Is(err, ErrInvalidValidatorSyntax) {
					ve = append(ve, ValidationError{FieldName: typeField.Name, Err: err})
				} else {
					return err
				}
			}
		}
	}

	if len(ve) == 0 {
		return nil
	}

	return ve
}

func doValidate(kind reflect.Kind, field reflect.Value, r string) error {
	tagArgs := strings.Split(r, ":")

	if len(tagArgs) != 2 {
		return ErrInvalidValidatorSyntax
	}

	if kind == reflect.String {
		switch tagArgs[0] {
		case "len":
			return validateLen(tagArgs[1], field.String())
		case "in":
			return validateIn(tagArgs[1], field, kind)
		case "min":
			return validateMinMaxString(tagArgs[1], field.String(), false)
		case "max":
			return validateMinMaxString(tagArgs[1], field.String(), true)
		default:
			return ErrInvalidValidatorSyntax
		}
	} else if kind == reflect.Int {
		switch tagArgs[0] {
		case "min":
			return validateMinMaxInt(tagArgs[1], field.Int(), false)
		case "max":
			return validateMinMaxInt(tagArgs[1], field.Int(), true)
		case "in":
			return validateIn(tagArgs[1], field, kind)
		default:
			return ErrInvalidValidatorSyntax
		}
	}

	return nil
}

func validateIn(inStr string, field reflect.Value, kind reflect.Kind) error {
	if inStr == "" {
		return ErrInvalidValidatorSyntax
	}

	in := strings.Split(inStr, ",")

	if kind == reflect.Int {
		for _, v := range in {
			vInt, err := strconv.Atoi(v)
			if err != nil {
				return ErrInvalidValidatorSyntax
			}

			if int64(vInt) == field.Int() {
				return nil
			}
		}
	} else if kind == reflect.String {
		for _, v := range in {
			if v == field.String() {
				return nil
			}
		}
	}

	return ErrInvalidValidatorSyntax
}

func validateMinMaxString(minMaxLen string, val string, flag bool) error {
	ln, err := strconv.Atoi(minMaxLen)
	if err != nil {
		return ErrInvalidValidatorSyntax
	}

	if flag && len(val) <= ln {
		return nil
	} else if !flag && len(val) >= ln {
		return nil
	}

	return ErrInvalidValidatorSyntax
}

func validateMinMaxInt(minMaxStr string, val int64, flag bool) error {
	minMax, err := strconv.Atoi(minMaxStr)
	if err != nil {
		return ErrInvalidValidatorSyntax
	}

	if flag && int64(minMax) >= val {
		return nil
	} else if !flag && int64(minMax) <= val {
		return nil
	}

	return ErrInvalidValidatorSyntax
}

func validateLen(ruleValStr string, fieldValStr string) error {
	ruleVal, err := strconv.Atoi(ruleValStr)
	if err != nil {
		return ErrInvalidValidatorSyntax
	}

	if ruleVal == len(fieldValStr) {
		return nil
	}

	return ErrInvalidValidatorSyntax
}
