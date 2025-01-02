import useServerCategories from "@/hooks/server/useCategories";
import RadioButton from "../atoms/RadioButton";
import Text from "../atoms/Text";

type CategorySelectorProps = {
  onChange: (categoryIds: string[]) => void;
  selectedCategories: string[];
};

const CategorySelector: React.FC<CategorySelectorProps> = ({
  onChange,
  selectedCategories,
}) => {
  const { categories } = useServerCategories();

  const onChangeHandler = (id: string) => {
    onChange([id]);
  };

  return (
    <div className="flex flex-col">
      <Text color="" size="">
        카테고리
      </Text>
      <div className="flex flex-row justify-center items-center gap-3">
        {categories?.map((category) => (
          <RadioButton
            key={category.id}
            options={category.name}
            name="categories"
            value={category.id}
            onChange={(e) => onChangeHandler(e.target.value)}
            checked={selectedCategories.includes(category.id)}
          />
        ))}
      </div>
    </div>
  );
};

export default CategorySelector;
